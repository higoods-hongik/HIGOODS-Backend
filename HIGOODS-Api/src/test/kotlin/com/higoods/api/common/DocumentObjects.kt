package com.higoods.api.common

import com.higoods.domain.user.domain.OauthProvider

object DocumentObjects : BaseControllerTest() {

    val userInfoVo: Array<DocumentField> = listOf(
        "user" type OBJECT means "UserDetailVo",
        "user.id" type NUMBER means "유저아이디",
        "user.nickname" type STRING means "유저닉네임",
        "user.profileImg" type STRING means "프로필 이미지 주소",
        "user.isDefaultImg" type BOOLEAN means "카카오 기본 이미지 여부",
        "user.oauthProvider" type STRING means "오어스 공급자 ${OauthProvider.values()}",
        "user.fcmInfo" type OBJECT means "FcmNotificationVo",
        "user.fcmInfo.fcmToken" type STRING means "FcmNotificationVo",
        "user.fcmInfo.appAlarm" type BOOLEAN means "FcmNotificationVo"
    ).toTypedArray()

    val authorizationHeaderDocs = requestHeaders("Authorization" type STRING means "bearerAuthJWT")

    override val controller: Any
        get() = OBJECT
}
