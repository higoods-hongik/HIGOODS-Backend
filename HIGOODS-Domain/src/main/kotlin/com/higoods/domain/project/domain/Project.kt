package com.higoods.domain.project.domain

import com.higoods.domain.common.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "tbl_project")
class Project(

    val userId: Long, // 유저 아이디

    var title: String, // 제목
    var titleImage: String, // 대표이미지
    var subTitle: String, // 한줄 소개

    var content: String, // 프로젝트 소개

    val minimumPurchaseQuantity: Long, // 최소 구매 인원

    var endDateTime: LocalDateTime, // 마감기한 날짜

    @Enumerated(EnumType.STRING)
    var shipmentType: ShipmentType // 배송 / 현장 배부 날짜

) : BaseEntity()
