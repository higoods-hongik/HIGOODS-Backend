package com.higoods.infra.api.kakao

import com.higoods.infra.api.kakao.config.KakaoInfoConfig
import com.higoods.infra.api.kakao.dto.KakaoInformationResponse
import com.higoods.infra.api.kakao.dto.UnlinkKaKaoTarget
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

// TODO : 에러 정책 확립되면 feign 에러 config 하기 , configuration = [KakaoInfoConfig::class]
@FeignClient(name = "KakaoInfoClient", url = "\${feign.kakao.info}", configuration = [KakaoInfoConfig::class])
interface KakaoInfoClient {
    @GetMapping("/v2/user/me")
    fun kakaoUserInfo(@RequestHeader("Authorization") accessToken: String): KakaoInformationResponse

    @PostMapping(path = ["/v1/user/unlink"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun unlinkUser(
        @RequestHeader("Authorization") adminKey: String,
        @RequestBody unlinkKaKaoTarget: UnlinkKaKaoTarget,
    )
}
