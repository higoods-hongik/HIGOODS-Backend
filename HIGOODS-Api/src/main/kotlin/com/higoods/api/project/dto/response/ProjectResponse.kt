package com.higoods.api.project.dto.response

import com.higoods.common.helper.toHigoodsDateTimeFormat
import com.higoods.domain.common.vo.UserInfoVo
import com.higoods.domain.project.domain.Project
import com.higoods.domain.project.domain.ShipmentType

data class ProjectResponse(
    val title: String,
    val titleImage: String,
    val subTitle: String,
    val content: String,
    val minimumPurchaseQuantity: Long, // 최소 구매 인원
    val endDateTime: String, // 마감기한 날짜
    val shipmentType: ShipmentType,
    val user: UserInfoVo,
    val createAt: String
) {
    companion object {
        fun of(project: Project, userInfo: UserInfoVo): ProjectResponse {
            return ProjectResponse(
                title = project.title,
                titleImage = project.titleImage,
                subTitle = project.subTitle,
                content = project.content,
                minimumPurchaseQuantity = project.minimumPurchaseQuantity,
                endDateTime = project.endDateTime.toHigoodsDateTimeFormat(),
                shipmentType = project.shipmentType,
                user = userInfo,
                createAt = project.createdAt.toHigoodsDateTimeFormat()
            )
        }
    }
}
