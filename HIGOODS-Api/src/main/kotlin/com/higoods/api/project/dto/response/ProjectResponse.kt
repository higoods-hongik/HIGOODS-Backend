package com.higoods.api.project.dto.response

import com.higoods.domain.common.vo.UserInfoVo
import com.higoods.domain.project.domain.Project
import com.higoods.domain.project.domain.ShipmentType
import java.time.LocalDateTime

data class ProjectResponse(
    val title: String,
    val titleImage: String,
    val subTitle: String,
    val content: String,
    val minimumPurchaseQuantity: Long, // 최소 구매 인원
    val endDateTime: LocalDateTime, // 마감기한 날짜
    val shipmentType: ShipmentType,
    val userInfo: UserInfoVo,
    val createAt: LocalDateTime
) {
    companion object {
        fun of(project: Project, userInfo: UserInfoVo): ProjectResponse {
            return ProjectResponse(
                title = project.title,
                titleImage = project.titleImage,
                subTitle = project.subTitle,
                content = project.content,
                minimumPurchaseQuantity = project.minimumPurchaseQuantity,
                endDateTime = project.endDateTime,
                shipmentType = project.shipmentType,
                userInfo = userInfo,
                createAt = project.createdAt
            )
        }
    }
}
