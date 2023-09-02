package com.higoods.api.auth

import com.higoods.api.auth.controller.AuthController
import com.higoods.api.auth.dto.request.LoginRequest
import com.higoods.api.auth.dto.request.RegisterRequest
import com.higoods.api.auth.dto.response.AbleRegisterResponse
import com.higoods.api.auth.dto.response.OauthLoginLinkResponse
import com.higoods.api.auth.dto.response.OauthTokenResponse
import com.higoods.api.auth.dto.response.OauthUserInfoResponse
import com.higoods.api.auth.dto.response.TokenAndUserResponse
import com.higoods.api.auth.usecase.LoginUseCase
import com.higoods.api.auth.usecase.LogoutUseCase
import com.higoods.api.auth.usecase.OauthUserInfoUseCase
import com.higoods.api.auth.usecase.RefreshUseCase
import com.higoods.api.auth.usecase.RegisterUserUseCase
import com.higoods.api.auth.usecase.WithDrawUseCase
import com.higoods.api.common.BOOLEAN
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
        test("/v1/auth/oauth/kakao/develop") {
            every { registerUseCase.upsertKakaoOauthUser(any()) } returns tokenAndUserResponse()

            get("/v1/auth/oauth/kakao/develop") {
                param("code", "파람 벨류")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "회원가입", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 회원가입을 할 수 있습니다."),
                    queryParameters(
                        "code" type STRING means "카카오 로그인시 넘겨저오는 토큰"
                    ),
                    tokenAndUserResponseDocs()
                )
        }

        test("/oauth/kakao/link/test") {
            val response = OauthLoginLinkResponse(
                link = "카카오 oauth 링크 발급"
            )
            every { registerUseCase.getKaKaoOauthLink() } returns response

            get("/v1/auth/oauth/kakao/link/test") {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급"),
                    responseFields(
                        "link" type STRING means "카카오 회원가입용 링크"
                    )
                )
        }

        test("/oauth/kakao/register") {
            val registerRequest = RegisterRequest(
                profileImage = "test",
                isDefaultImage = false,
                nickname = "nickname",
                fcmToken = "fcm",
                appAlarm = false
            )
            every { registerUseCase.registerUserByOCIDToken(any(), any()) } returns tokenAndUserResponse()

            post("/v1/auth/oauth/kakao/register", registerRequest) {
                param("id_token", "카카오 oauth 인증 이후에 발급되는 oauth 토큰")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급"),
                    tokenAndUserResponseDocs()
                )
        }

        test("/oauth/kakao") {
            val response = OauthTokenResponse(
                accessToken = "accessToken",
                refreshToken = "refreshToken",
                idToken = "idToken"
            )
            every { registerUseCase.getCredentialFromKaKao(any()) } returns response

            get("/v1/auth/oauth/kakao") {
                param("code", "파람 벨류")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급"),
                    responseFields(
                        "accessToken" type STRING means "카카오의 accessToken 토큰",
                        "refreshToken" type STRING means "카카오의 refreshToken 토큰",
                        "idToken" type STRING means "카카오의 idToken 토큰"
                    )
                )
        }

        test("/oauth/kakao/register/valid") {
            val response = AbleRegisterResponse(
                canRegister = true
            )
            every { registerUseCase.checkAvailableRegister(any()) } returns response

            get("/v1/auth/oauth/kakao/register/valid") {
                param("id_token", "카카오 oauth 인증 이후에 발급되는 oauth 토큰")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급"),
                    responseFields(
                        "canRegister" type BOOLEAN means "회원가입 가능한여부"
                    )
                )
        }

        test("/oauth/kakao/info") {
            val response = OauthUserInfoResponse(
                profileImage = "프로필 이미지",
                isDefaultImage = false,
                nickname = "nickname"
            )
            every { oauthUserInfoUseCase.execute(any()) } returns response

            get("/v1/auth/oauth/kakao/info") {
                param("access_token", "카카오 oauth 인증 이후에 발급되는 oauth 토큰")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급"),
                    responseFields(
                        "profileImage" type STRING means "프로필 이미지",
                        "isDefaultImage" type BOOLEAN means "회원가입 가능한여부",
                        "nickname" type STRING means "닉네임"
                    )
                )
        }

        test("/oauth/kakao/login") {
            val loginRequest = LoginRequest(fcmToken = "fcmToken")
            every { loginUseCase.execute(any(), any()) } returns tokenAndUserResponse()

            post("/v1/auth/oauth/kakao/login", loginRequest) {
                param("id_token", "카카오 oauth 인증 이후에 발급되는 oauth 토큰")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급"),
                    tokenAndUserResponseDocs()
                )
        }

        test("/token/refresh") {
            every { refreshUseCase.execute(any()) } returns tokenAndUserResponse()

            post("/v1/auth/token/refresh") {
                param("token", "우리 리프레쉬 토큰")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급"),
                    tokenAndUserResponseDocs()
                )
        }

        // -- 인증 토큰이 필요한 api 입니다.
        test("/logout") {
            every { logoutUseCase.execute() } returns Unit

            post("/v1/auth/logout") {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급")
                )
        }

        test("/me") {
            every { withDrawUseCase.execute() } returns Unit

            delete("/v1/auth/me") {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "카카오 oauth 링크 발급", tag = OpenApiTag.AUTH, description = "백엔드 환경에서 카카오 개발용 테스트 링크 발급")
                )
        }
    }

    private fun tokenAndUserResponseDocs() = responseFields(
        *DocumentObjects.userInfoVo,
        "accessTokenExpireIn" type NUMBER means "Access 토큰 만료시간",
        "accessToken" type STRING means "Access 토큰 ",
        "refreshToken" type STRING means "refreshToken 토큰",
        "refreshTokenExpireIn" type NUMBER means "Refresh 토큰 말뇨시간"
    )

    private fun tokenAndUserResponse() = TokenAndUserResponse(
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
}
