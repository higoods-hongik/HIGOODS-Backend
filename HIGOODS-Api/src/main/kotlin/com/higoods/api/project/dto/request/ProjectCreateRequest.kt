package com.higoods.api.project.dto.request

import com.higoods.api.config.security.SecurityUtils
import com.higoods.domain.project.domain.Project
import com.higoods.domain.project.domain.ShipmentType
import java.time.LocalDateTime

data class ProjectCreateRequest(
    val title: String,
    val titleImage: String,
    val subTitle: String,
    val content: String,
    val minimumPurchaseQuantity: Long, // 최소 구매 인원
    val endDateTime: LocalDateTime, // 마감기한 날짜
    val shipmentType: ShipmentType
) {
    fun toProject(): Project {
        return Project(
            userId = SecurityUtils.currentUserId,
            title = this.title,
            titleImage = this.titleImage,
            subTitle = this.subTitle,
            content = this.content,
            minimumPurchaseQuantity = this.minimumPurchaseQuantity,
            endDateTime = this.endDateTime,
            shipmentType = this.shipmentType
        )
    }
}
