package com.higoods.api.common

import com.higoods.domain.user.domain.OauthProvider
import org.springframework.restdocs.payload.ResponseFieldsSnippet

object DocumentObjects : BaseControllerTest() {

    val userDetailVo: Array<DocumentField> = listOf(
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

    val userInfoVo: Array<DocumentField> = listOf(
        "user" type OBJECT means "UserDetailVo",
        "user.id" type NUMBER means "유저아이디",
        "user.nickname" type STRING means "유저닉네임",
        "user.profileImg" type STRING means "프로필 이미지 주소",
        "user.isDefaultImg" type BOOLEAN means "카카오 기본 이미지 여부"
    ).toTypedArray()

    fun getPageResponse(elementDocs: Array<DocumentField>): ResponseFieldsSnippet {
        return responseFields(
            "content[]" type ARRAY means "content",
            "pageable" type STRING means "페이지정보",
            "totalPages" type NUMBER means "총 페이지",
            "totalElements" type NUMBER means "총 요소 숫자",
            "last" type BOOLEAN means "마지막 페이지 인지",
            "number" type NUMBER means "현재 페이지 번호",
            "size" type NUMBER means "페이지 크기",
            "sort.empty" type BOOLEAN means "정렬 필드가 비어있는지 여부",
            "sort.unsorted" type BOOLEAN means "정렬된 상태 여부",
            "sort.sorted" type BOOLEAN means "정렬되지 않은 상태 여부",
            "numberOfElements" type NUMBER means "현재 페이지의 요소 수",
            "first" type BOOLEAN means "첫 페이지 여부",
            "empty" type BOOLEAN means "데이터 비어있음 여부"
        ).andWithPrefix("content[].", elementDocs.map { it.toFieldDescriptor() })
    }

    val dateTimeFormatString: String = "1999-11-27T11:22"

    override val controller: Any
        get() = OBJECT
}
