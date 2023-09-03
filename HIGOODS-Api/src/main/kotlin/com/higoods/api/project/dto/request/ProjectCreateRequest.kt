package com.higoods.api.project.dto.request

import com.higoods.common.helper.toHigoodsDateTimeFormat
import com.higoods.domain.project.domain.Project
import com.higoods.domain.project.domain.ShipmentType

data class ProjectCreateRequest(
    val title: String,
    val titleImage: String,
    val subTitle: String,
    val minimumPurchaseQuantity: Long, // 최소 구매 인원
    val endDateTime: String, // 마감기한 날짜
    val shipmentType: ShipmentType
) {
    fun toProject(userId: Long): Project {
        return Project(
            userId = userId,
            title = this.title,
            titleImage = this.titleImage,
            subTitle = this.subTitle,
            minimumPurchaseQuantity = this.minimumPurchaseQuantity,
            endDateTime = endDateTime.toHigoodsDateTimeFormat(),
            shipmentType = this.shipmentType
        )
    }
}
