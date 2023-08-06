package com.higoods.domain.notification.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_notification")
class Notification(

    val projectId: Long, // 프로젝트 아이디

    var title: String, // 제목
    var content: String // 내용

) : BaseEntity()
