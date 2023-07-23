package com.higoods.api.auth.usecase

import com.higoods.api.auth.dto.request.RegisterRequest
import com.higoods.api.auth.dto.response.AbleRegisterResponse
import com.higoods.api.auth.dto.response.OauthLoginLinkResponse
import com.higoods.api.auth.dto.response.OauthTokenResponse
import com.higoods.api.auth.dto.response.TokenAndUserResponse
import com.higoods.api.auth.helper.KakaoOauthHelper
import com.higoods.api.auth.helper.OauthUserInfoDto
import com.higoods.api.auth.usecase.helper.TokenGenerateHelper
import com.higoods.common.annotation.UseCase
import com.higoods.domain.user.domain.OauthInfo
import com.higoods.domain.user.domain.User
import com.higoods.domain.user.service.UserDomainService

@UseCase
class RegisterUserUseCase(
    val userDomainService: UserDomainService,
    val kakaoOauthHelper: KakaoOauthHelper,
    val tokenGenerateHelper: TokenGenerateHelper,
) {

    /**
     * 백엔드 개발용으로 회원가입, 로그인 처리를 합니다.
     * Oauth accessToken 을 통해 바로 회원가입.로그인을 합니다.
     */
    fun upsertKakaoOauthUser(code: String): TokenAndUserResponse {
        val oauthAccessToken: String = kakaoOauthHelper.getOauthToken(code).accessToken
        val oauthUserInfo: OauthUserInfoDto = kakaoOauthHelper.getUserInfo(oauthAccessToken)
        val user: User = userDomainService.upsertUser(oauthUserInfo.username, oauthUserInfo.profileImage, oauthUserInfo.isDefaultImage, oauthUserInfo.toOauthInfo(), oauthUserInfo.oauthId)
        return tokenGenerateHelper.execute(user)
    }

    /**
     * 백엔드 개발용으로 oauth 로그인할 링크를 발급 받습니다.
     */
    fun getKaKaoOauthLink(): OauthLoginLinkResponse {
        return OauthLoginLinkResponse(kakaoOauthHelper.kaKaoOauthLink())
    }

    /**
     * 백엔드 개발용으로 code 요청온것을 받아서 Oauth 토큰을 발급받습니다.
     * accessToken, refreshToken, idToken을 발급받을 수 있습니다.
     */
    fun getCredentialFromKaKao(code: String): OauthTokenResponse {
        val tokens = kakaoOauthHelper.getOauthToken(code)
        return OauthTokenResponse(tokens.accessToken, tokens.refreshToken, tokens.idToken)
    }

    /**
     * 어플리케이션에서 발급받은 idToken 을 통해 oauth 로그인 세션정보를 검증하고,
     * registerRequest 의 유저정보로 유저회원가입 처리를 합니다.
     */
    fun registerUserByOCIDToken(idToken: String, registerRequest: RegisterRequest): TokenAndUserResponse {
        val oauthInfo = kakaoOauthHelper.getOauthInfoByIdToken(idToken)
        val user: User = userDomainService.registerUser(
            registerRequest.nickname,
            registerRequest.profileImage,
            registerRequest.isDefaultImage,
            oauthInfo,
            oauthInfo.oauthId,
            registerRequest.fcmToken,
            registerRequest.appAlarm,
        )

        return tokenGenerateHelper.execute(user)
    }

    /**
     * 어플리케이션에서 로그인 or 회원가입시 이미 등록한 유저인지 판단할 수 없으므로,
     * 한번 회원가입한 유저인지 확인하는 작업을 거칩니다.
     */
    fun checkAvailableRegister(idToken: String): AbleRegisterResponse {
        val oauthInfo: OauthInfo = kakaoOauthHelper.getOauthInfoByIdToken(idToken)
        return AbleRegisterResponse(userDomainService.checkUserCanRegister(oauthInfo))
    }
}
