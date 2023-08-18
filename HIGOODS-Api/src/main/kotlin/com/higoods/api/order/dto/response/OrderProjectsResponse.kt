package com.higoods.api.order.dto.response

import com.higoods.domain.project.domain.Project

data class OrderProjectsResponse(
    val orderId: Long,
    val title: String,
    val titleImage: String,
    val subTitle: String
    // TODO: 프로젝트단 관련 작업 완료되면 추가 작업 필요
    // val category: String,
    // TODO: 프로젝트 상채 도메인 개발 후 추가 작업 필요
    // val projectStatus: String
) {
    companion object {
        fun of(orderId: Long, project: Project): OrderProjectsResponse {
            return OrderProjectsResponse(
                orderId = orderId,
                title = project.title,
                titleImage = project.titleImage,
                subTitle = project.subTitle
            )
        }
    }
}
