package com.higoods.api.auth

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.epages.restdocs.apispec.Schema
import com.higoods.api.auth.controller.AuthController
import com.higoods.api.auth.dto.response.TokenAndUserResponse
import com.higoods.api.auth.usecase.LoginUseCase
import com.higoods.api.auth.usecase.LogoutUseCase
import com.higoods.api.auth.usecase.OauthUserInfoUseCase
import com.higoods.api.auth.usecase.RefreshUseCase
import com.higoods.api.auth.usecase.RegisterUserUseCase
import com.higoods.api.auth.usecase.WithDrawUseCase
import com.higoods.api.common.BaseControllerTest
import com.higoods.domain.common.vo.UserDetailVo
import com.higoods.domain.user.domain.FcmNotificationVo
import com.higoods.domain.user.domain.OauthProvider
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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

            mockMvc.perform(
                get("/v1/auth/oauth/kakao/develop")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("code", "카카오 코드 벨류 oauth 인증 이후 값을 받습니다.")
            )
                .andExpect(status().isOk())
                .andDo {
                    MockMvcRestDocumentationWrapper.document(
                        identifier = "백엔드 환경에서 카카오 개발용 회원가입을 할 수 있습니다.",
                        resourceDetails = ResourceSnippetParametersBuilder()
                            .tag("Sample")
                            .description("자동으로 code 요청을 받아서 수행합니다.")
                            .requestParameters(
                                parameterWithName("code").description("The page to be requested.")
                            )
                            .responseSchema(
                                Schema("SliceResponseEventProfileResponse")
                            )
                            .responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("currentTime").type(JsonFieldType.STRING)
                                    .description("현재시각")
                            )
                    )
                }
        }
    }
}
