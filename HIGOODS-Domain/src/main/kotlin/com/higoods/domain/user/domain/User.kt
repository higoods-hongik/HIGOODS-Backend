package com.higoods.domain.user.domain

import com.higoods.domain.common.BaseEntity
import com.higoods.domain.common.event.Events
import com.higoods.domain.common.vo.UserDetailVo
import com.higoods.domain.common.vo.UserInfoVo
import com.higoods.domain.events.UserRegisterEvent
import com.higoods.domain.user.exception.AlreadyDeletedUserException
import com.higoods.domain.user.exception.ForbiddenUserException
import java.time.LocalDateTime
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.PostPersist
import javax.persistence.Table

@Entity
@Table(name = "tbl_user")
class User(

    @Embedded
    var oauthInfo: OauthInfo,

    var nickname: String,

    var profileImg: String, // 프로필 이미지도 vo 로 빼면 더 이쁠듯
    var isDefaultImg: Boolean,

    @Embedded
    var fcmNotification: FcmNotificationVo,

    var lastLoginAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.NORMAL,

    @Enumerated(EnumType.STRING)
    var accountRole: AccountRole = AccountRole.USER,

    var studentId: String,
    var department: String,
    var certificationLevel: CertificationLevel

) : BaseEntity() {

    @PostPersist
    fun registerEvent() {
        Events.raise(UserRegisterEvent(this.id))
    }

    fun login(fcmToken: String) {
        if (status != UserStatus.NORMAL) {
            throw ForbiddenUserException.EXCEPTION
        }
        lastLoginAt = LocalDateTime.now()
        updateToken(fcmToken) // 로그인시에 토큰 업데이트
    }

    fun withDraw() {
        if (status == UserStatus.DELETED) {
            throw AlreadyDeletedUserException.EXCEPTION
        }
        status = UserStatus.DELETED
        nickname = "탈퇴유저"
        profileImg = ""
        fcmNotification = FcmNotificationVo.disableAlarm(this.fcmNotification)
        oauthInfo = oauthInfo.withDrawOauthInfo()
    }

    private fun updateToken(fcmToken: String) {
        fcmNotification = FcmNotificationVo.updateToken(this.fcmNotification, fcmToken)
    }

    fun refresh() {
        if (status != UserStatus.NORMAL) {
            throw ForbiddenUserException.EXCEPTION
        }
        lastLoginAt = LocalDateTime.now()
    }

    fun toggleAppAlarmState() {
        fcmNotification = FcmNotificationVo.toggleAlarm(fcmNotification)
    }

    fun updateFcmToken(fcmToken: String) {
        fcmNotification = FcmNotificationVo.updateToken(fcmNotification, fcmToken)
    }

    fun toUserInfoVo(): UserInfoVo {
        return UserInfoVo.from(this)
    }

    fun toUserDetailVo(): UserDetailVo {
        return UserDetailVo.from(this)
    }
}
