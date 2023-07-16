package com.higoods.infra.api.kakao

import com.higoods.infra.api.kakao.config.KakaoKauthConfig
import com.higoods.infra.api.kakao.dto.KakaoTokenResponse
import com.higoods.infra.api.kakao.dto.OIDCPublicKeysResponse
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

// TODO : , configuration = [KakaoKauthConfig::class] 애러정책 확립후 클라이언트 설정 들어가기
@FeignClient(name = "KakaoAuthClient", url = "\${feign.kakao.oauth}", configuration = [KakaoKauthConfig::class])
interface KakaoOauthClient {
    @PostMapping("/oauth/token?grant_type=authorization_code&client_id={CLIENT_ID}&redirect_uri={REDIRECT_URI}&code={CODE}&client_secret={CLIENT_SECRET}")
    fun kakaoAuth(
        @PathVariable("CLIENT_ID") clientId: String,
        @PathVariable("REDIRECT_URI") redirectUri: String,
        @PathVariable("CODE") code: String,
        @PathVariable("CLIENT_SECRET") client_secret: String,
    ): KakaoTokenResponse

    @GetMapping("/.well-known/jwks.json")
    @Cacheable(cacheNames = ["KakaoOIDC"], cacheManager = "oidcCacheManager")
    fun kakaoOIDCOpenKeys(): OIDCPublicKeysResponse
}
