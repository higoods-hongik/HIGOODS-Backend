package com.higoods.api.auth.usecase

import com.higoods.api.auth.dto.response.OauthUserInfoResponse
import com.higoods.api.auth.helper.KakaoOauthHelper
import com.higoods.api.auth.helper.OauthUserInfoDto
import com.higoods.common.annotation.UseCase

@UseCase
class OauthUserInfoUseCase(
    val kakaoOauthHelper: KakaoOauthHelper
) {

    /**
     * 어세스토큰으로 해당 유저의 정보를 가져옵니다.
     */
    fun execute(accessToken: String): OauthUserInfoResponse {
        val oauthUserInfo: OauthUserInfoDto = kakaoOauthHelper.getUserInfo(accessToken)
        return OauthUserInfoResponse.from(oauthUserInfo)
    }
}
