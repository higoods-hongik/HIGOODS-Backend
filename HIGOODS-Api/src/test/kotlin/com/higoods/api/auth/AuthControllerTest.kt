package com.higoods.api.auth

import com.higoods.api.auth.controller.AuthController
import com.higoods.api.auth.dto.response.TokenAndUserResponse
import com.higoods.api.auth.usecase.LoginUseCase
import com.higoods.api.auth.usecase.LogoutUseCase
import com.higoods.api.auth.usecase.OauthUserInfoUseCase
import com.higoods.api.auth.usecase.RefreshUseCase
import com.higoods.api.auth.usecase.RegisterUserUseCase
import com.higoods.api.auth.usecase.WithDrawUseCase
import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.DocumentObjects
import com.higoods.api.common.NUMBER
import com.higoods.api.common.OpenApiTag
import com.higoods.api.common.STRING
import com.higoods.domain.common.vo.UserDetailVo
import com.higoods.domain.user.domain.FcmNotificationVo
import com.higoods.domain.user.domain.OauthProvider
import io.mockk.every
import io.mockk.mockk

class AuthControllerTest : BaseControllerTest() {
    private val registerUseCase: RegisterUserUseCase = mockk()
    private val oauthUserInfoUseCase: OauthUserInfoUseCase = mockk()
    private val loginUseCase: LoginUseCase = mockk()
    private val refreshUseCase: RefreshUseCase = mockk()
    private val logoutUseCase: LogoutUseCase = mockk()
    private val withDrawUseCase: WithDrawUseCase = mockk()

    override val controller: AuthController = AuthController(
        registerUseCase = registerUseCase,
        oauthUserInfoUseCase = oauthUserInfoUseCase,
        loginUseCase = loginUseCase,
        refreshUseCase = refreshUseCase,
        logoutUseCase = logoutUseCase,
        withDrawUseCase = withDrawUseCase
    )

    init {
        test("카카오톡 링크를 받습니다.") {
            val testResponse = TokenAndUserResponse(
                accessToken = "어세스토큰 정보입니다.",
                refreshToken = "리프레쉬 토큰 정보입니다.",
                user = UserDetailVo(
                    id = 1,
                    profileImg = "프로필 이미지 주소",
                    nickname = "닉네임",
                    isDefaultImg = true,
                    oauthProvider = OauthProvider.KAKAO,
                    fcmInfo = FcmNotificationVo(
                        fcmToken = "알림 토큰 정보 입니다.",
                        appAlarm = false
                    )

                ),
                accessTokenExpireIn = 3600,
                refreshTokenExpireIn = 3600
            )
            every { registerUseCase.upsertKakaoOauthUser(any()) } returns testResponse

            get("/v1/auth/oauth/kakao/develop") {
                param("code", "파람 벨류")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "test", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 회원가입을 할 수 있습니다."),
                    queryParameters(
                        "code" type STRING means "카카오 로그인시 넘겨저오는 토큰"
                    ),
                    responseFields(
                        *DocumentObjects.userInfoVo,
                        "accessTokenExpireIn" type NUMBER means "Access 토큰 만료시간",
                        "accessToken" type STRING means "Access 토큰 ",
                        "refreshToken" type STRING means "refreshToken 토큰",
                        "refreshTokenExpireIn" type NUMBER means "Refresh 토큰 말뇨시간"
                    )
                )
        }
    }
}
