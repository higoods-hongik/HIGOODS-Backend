package com.higoods.api.auth.helper

import com.depromeet.whatnow.config.jwt.OIDCDecodePayload
import com.higoods.common.annotation.Helper
import com.higoods.common.const.BEARER
import com.higoods.domain.user.domain.OauthInfo
import com.higoods.domain.user.domain.OauthProvider
import com.higoods.infra.api.kakao.KakaoInfoClient
import com.higoods.infra.api.kakao.KakaoOauthClient
import com.higoods.infra.api.kakao.dto.KakaoInformationResponse
import com.higoods.infra.api.kakao.dto.KakaoTokenResponse
import com.higoods.infra.api.kakao.dto.OIDCPublicKeysResponse
import com.higoods.infra.api.kakao.dto.UnlinkKaKaoTarget
import com.higoods.infra.config.OauthProperties

@Helper
class KakaoOauthHelper(
    val oauthProperties: OauthProperties,
    val kakaoInfoClient: KakaoInfoClient,
    val kakaoOauthClient: KakaoOauthClient,
    val oauthOIDCHelper: OauthOIDCHelper
) {
    var kakaoOauth: OauthProperties.OAuthSecret = oauthProperties.kakao

    fun kaKaoOauthLink(): String {
        return kakaoOauth.baseUrl +
            "/oauth/authorize?client_id=${kakaoOauth.clientId}" +
            "&redirect_uri=${kakaoOauth.redirectUrl}" +
            "&response_type=code"
    }

    fun getOauthToken(code: String): KakaoTokenResponse {
        return kakaoOauthClient.kakaoAuth(
            kakaoOauth.clientId,
            kakaoOauth.redirectUrl,
            code,
            kakaoOauth.clientSecret
        )
    }

    fun getUserInfo(oauthAccessToken: String): OauthUserInfoDto {
        val response = kakaoInfoClient.kakaoUserInfo(BEARER + oauthAccessToken)
        val kakaoAccount: KakaoInformationResponse.KakaoAccount = response.kakaoAccount
        return OauthUserInfoDto(response.id, kakaoAccount.profile.profileImageUrl, kakaoAccount.profile.isDefaultImage, kakaoAccount.profile.nickname, OauthProvider.KAKAO)
    }

    fun getOIDCDecodePayload(token: String): OIDCDecodePayload {
        val oidcPublicKeysResponse: OIDCPublicKeysResponse = kakaoOauthClient.kakaoOIDCOpenKeys()
        return oauthOIDCHelper.getPayloadFromIdToken(
            token,
            kakaoOauth.baseUrl,
            kakaoOauth.appId,
            oidcPublicKeysResponse
        )
    }

    fun getOauthInfoByIdToken(idToken: String): OauthInfo {
        val oidcDecodePayload: OIDCDecodePayload = getOIDCDecodePayload(idToken)
        return OauthInfo(oidcDecodePayload.sub, OauthProvider.KAKAO)
    }

    fun unlink(oid: String) {
        val kakaoAdminKey: String = oauthProperties.kakao.adminKey
        val unlinkKaKaoTarget = UnlinkKaKaoTarget(oid)
        val header = "KakaoAK $kakaoAdminKey"
        kakaoInfoClient.unlinkUser(header, unlinkKaKaoTarget)
    }
}
