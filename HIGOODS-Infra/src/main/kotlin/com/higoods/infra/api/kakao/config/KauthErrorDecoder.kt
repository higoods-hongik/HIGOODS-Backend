package com.higoods.infra.api.kakao.config

import com.depromeet.whatnow.api.dto.KakaoKauthErrorResponse
import com.higoods.common.exception.HiGoodsDynamicException
import com.higoods.common.exception.dto.ErrorReason
import com.higoods.infra.api.kakao.config.KakaoKauthErrorCode.KOE_INVALID_REQUEST
import feign.Response
import feign.codec.ErrorDecoder

class KauthErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        val body: KakaoKauthErrorResponse = KakaoKauthErrorResponse.from(response)
        try {
            val kakaoKauthErrorCode = KakaoKauthErrorCode.valueOf(body.errorCode)
            val errorReason: ErrorReason = kakaoKauthErrorCode.errorReason
            throw HiGoodsDynamicException(
                errorReason.status,
                errorReason.code,
                errorReason.reason
            )
        } catch (e: IllegalArgumentException) {
            val koeInvalidRequest = KOE_INVALID_REQUEST
            val errorReason: ErrorReason = koeInvalidRequest.errorReason
            throw HiGoodsDynamicException(
                errorReason.status,
                errorReason.code,
                errorReason.reason
            )
        }
    }
}
