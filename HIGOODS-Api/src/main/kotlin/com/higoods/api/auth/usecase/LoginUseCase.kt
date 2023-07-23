package com.higoods.api.auth.usecase

import com.higoods.api.auth.dto.request.LoginRequest
import com.higoods.api.auth.dto.response.TokenAndUserResponse
import com.higoods.api.auth.helper.KakaoOauthHelper
import com.higoods.api.auth.usecase.helper.TokenGenerateHelper
import com.higoods.common.annotation.UseCase
import com.higoods.domain.user.domain.OauthInfo
import com.higoods.domain.user.domain.User
import com.higoods.domain.user.service.UserDomainService

@UseCase
class LoginUseCase(
    val kakaoOauthHelper: KakaoOauthHelper,
    val userDomainService: UserDomainService,
    val tokenGenerateHelper: TokenGenerateHelper,
) {
    fun execute(idToken: String, loginRequest: LoginRequest): TokenAndUserResponse {
        val oauthInfo: OauthInfo = kakaoOauthHelper.getOauthInfoByIdToken(idToken)
        val user: User = userDomainService.loginUser(oauthInfo, loginRequest.fcmToken)
        return tokenGenerateHelper.execute(user)
    }
}
