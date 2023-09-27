package com.higoods.api.order.dto.response

import com.higoods.domain.item.domain.ProductCategory
import com.higoods.domain.order.domain.OrderState
import com.higoods.domain.project.domain.Project
import com.higoods.domain.projectStatus.domain.ProjectStatus

data class OrderProjectsResponse(
    val orderId: Long,
    val projectId: Long,
    val title: String,
    val titleImage: String,
    val subTitle: String,
    val orderState: OrderState,
    val category: ProductCategory,
    val projectStatus: String
) {
    companion object {
        fun of(orderId: Long, orderState: OrderState, project: Project, category: ProductCategory, projectStatus: ProjectStatus): OrderProjectsResponse {
            return OrderProjectsResponse(
                orderId = orderId,
                projectId = project.id,
                title = project.title,
                titleImage = project.titleImage,
                subTitle = project.subTitle,
                orderState = orderState,
                category = category,
                projectStatus = projectStatus.keyword
            )
        }
    }
}
