package com.higoods.api.auth.helper

import com.higoods.domain.user.domain.OauthInfo
import com.higoods.domain.user.domain.OauthProvider


data class OauthUserInfoDto(
    val oauthId: String,
    val profileImage: String,
    val isDefaultImage: Boolean,
    val username: String,
    val oauthProvider: OauthProvider,
) {
    fun toOauthInfo(): OauthInfo {
        return OauthInfo(oauthId, oauthProvider)
    }
}
