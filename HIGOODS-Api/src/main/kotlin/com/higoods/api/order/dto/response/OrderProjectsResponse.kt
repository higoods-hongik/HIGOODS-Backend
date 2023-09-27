package com.higoods.api.order.dto.response

import com.higoods.domain.item.domain.ProductCategory
import com.higoods.domain.order.domain.OrderState
import com.higoods.domain.project.domain.Project

data class OrderProjectsResponse(
    val orderId: Long,
    val title: String,
    val titleImage: String,
    val subTitle: String,
    val orderState: OrderState,
    val category: ProductCategory
    // TODO: 프로젝트 상태 도메인 개발 후 추가 작업 필요
    // val projectStatus: String
) {
    companion object {
        fun of(orderId: Long, orderState: OrderState, project: Project, category: ProductCategory): OrderProjectsResponse {
            return OrderProjectsResponse(
                orderId = orderId,
                title = project.title,
                titleImage = project.titleImage,
                subTitle = project.subTitle,
                orderState = orderState,
                category = category
            )
        }
    }
}
