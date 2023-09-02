package com.higoods.api.auth.controller

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
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// @Tag(name = "1-1. [인증]")
@RestController
@RequestMapping("/v1/auth")
class AuthController(
    val registerUseCase: RegisterUserUseCase,
    val oauthUserInfoUseCase: OauthUserInfoUseCase,
    val loginUseCase: LoginUseCase,
    val refreshUseCase: RefreshUseCase,
    val logoutUseCase: LogoutUseCase,
    val withDrawUseCase: WithDrawUseCase
) {

//    @Operation(summary = "개발용 회원가입입니다 ( 백엔드용 )", deprecated = true)
    @GetMapping("/oauth/kakao/develop")
    fun developUserSign(@RequestParam("code") code: String): TokenAndUserResponse {
        return registerUseCase.upsertKakaoOauthUser(code)
    }

//    @Operation(summary = "kakao oauth 링크발급 ( 백엔드용 )", description = "kakao 링크를 받아볼수 있습니다.")
    @GetMapping("/oauth/kakao/link/test")
    fun getKakaoOauthLink(): OauthLoginLinkResponse {
        return registerUseCase.getKaKaoOauthLink()
    }

//    @Operation(summary = "카카오 code 요청받는 곳입니다. ( 백엔드 용 ) ")
    @GetMapping("/oauth/kakao")
    fun getCredentialFromKaKao(
        @RequestParam("code") code: String
    ): OauthTokenResponse {
        return registerUseCase.getCredentialFromKaKao(code)
    }

//    @Operation(summary = "id_token 으로 회원가입을 합니다.")
    @PostMapping("/oauth/kakao/register")
    fun kakaoAuthCheckRegisterValid(
        @RequestParam("id_token") token: String,
        @Validated @RequestBody
        registerRequest: RegisterRequest
    ): TokenAndUserResponse {
        return registerUseCase.registerUserByOCIDToken(token, registerRequest)
    }

//    @Operation(summary = "회원가입이 가능한지 id token 으로 확인합니다.")
    @GetMapping("/oauth/kakao/register/valid")
    fun kakaoAuthCheckRegisterValid(
        @RequestParam("id_token") token: String
    ): AbleRegisterResponse {
        return registerUseCase.checkAvailableRegister(token)
    }

//    @Operation(summary = "accessToken 으로 oauth user 정보를 가져옵니다.")
    @GetMapping("/oauth/kakao/info")
    fun kakaoOauthUserInfo(
        @RequestParam("access_token") accessToken: String
    ): OauthUserInfoResponse {
        return oauthUserInfoUseCase.execute(accessToken)
    }

//    @Operation(summary = "id_token 으로 로그인을 합니다.")
//    @Tag(name = "1-2. [카카오]")
    @PostMapping("/oauth/kakao/login")
    fun kakaoOauthUserLogin(
        @RequestParam("id_token") token: String,
        @Validated @RequestBody
        loginRequest: LoginRequest
    ): TokenAndUserResponse {
        return loginUseCase.execute(token, loginRequest)
    }

//    @Operation(summary = "refreshToken 용입니다.")
    @PostMapping("/token/refresh")
    fun tokenRefresh(
        @RequestParam(value = "token") refreshToken: String
    ): TokenAndUserResponse {
        return refreshUseCase.execute(refreshToken)
    }

//    @Operation(summary = "로그아웃을 합니다.")
//    @SecurityRequirement(name = "access-token")
    @PostMapping("/logout")
    fun logoutUser() {
        logoutUseCase.execute()
    }

//    @Operation(summary = "회원탈퇴를 합니다.")
//    @SecurityRequirement(name = "access-token")
    @DeleteMapping("/me")
    fun withDrawUser() {
        withDrawUseCase.execute()
    }
}
