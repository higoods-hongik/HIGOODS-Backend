package com.higoods.domain.common.vo

import com.higoods.domain.user.domain.FcmNotificationVo
import com.higoods.domain.user.domain.OauthProvider
import com.higoods.domain.user.domain.User

class UserDetailVo(
    val id: Long,
    val profileImg: String,
    val nickname: String,
    val isDefaultImg: Boolean,
    val oauthProvider: OauthProvider,
    val fcmInfo: FcmNotificationVo
) {
    companion object {
        fun from(user: User): UserDetailVo {
            return UserDetailVo(
                user.id,
                user.profileImg,
                user.nickname,
                user.isDefaultImg,
                user.oauthInfo.oauthProvider,
                user.fcmNotification
            )
        }
    }
}
