package com.higoods.api.auth.helper

import com.depromeet.whatnow.config.jwt.OIDCDecodePayload
import com.higoods.api.config.jwt.JwtOIDCHelper
import com.higoods.common.annotation.Helper
import com.higoods.infra.api.kakao.dto.OIDCPublicKeyDto
import com.higoods.infra.api.kakao.dto.OIDCPublicKeysResponse

@Helper
class OauthOIDCHelper(
    val jwtOIDCProvider: JwtOIDCHelper
) {

    /**
     * idToken 의 공개키 검증과 함께
     * 토큰안에 들어있는 payload 의 정보를 가져옵니다.
     */
    fun getPayloadFromIdToken(
        token: String, // id Token
        iss: String, // 발행자
        aud: String, // 유저의 고유 oauth id
        oidcPublicKeysResponse: OIDCPublicKeysResponse
    ): OIDCDecodePayload {
        val kid = getKidFromUnsignedIdToken(token, iss, aud)
        val oidcPublicKeyDto: OIDCPublicKeyDto = oidcPublicKeysResponse.keys.stream()
            .filter { o -> o.kid == kid }
            .findFirst()
            .orElseThrow()
        return jwtOIDCProvider.getOIDCTokenBody(
            token,
            oidcPublicKeyDto.n,
            oidcPublicKeyDto.e
        )
    }

    private fun getKidFromUnsignedIdToken(token: String, iss: String, aud: String): String {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud)
    }
}
