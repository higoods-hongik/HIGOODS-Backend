package com.higoods.api.order.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.order.dto.response.OrderAdminResponse
import com.higoods.api.order.dto.response.OrderProjectsResponse
import com.higoods.api.order.dto.response.OrderResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.item.adapter.ItemAdapter
import com.higoods.domain.order.adapter.OrderAdapter
import com.higoods.domain.order.domain.OrderState
import com.higoods.domain.order.exception.OrderNotUserException
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.exception.ProjectNotHostException
import com.higoods.domain.projectStatus.adapter.ProjectStatusAdapter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional

@UseCase
class OrderReadUseCase(
    private val orderAdapter: OrderAdapter,
    private val projectAdapter: ProjectAdapter,
    private val itemAdapter: ItemAdapter,
    private val projectStatusAdapter: ProjectStatusAdapter
) {
    @Transactional(readOnly = true)
    fun findAll(): List<OrderProjectsResponse> {
        val orders = orderAdapter.findAll(SecurityUtils.currentUserId)
        if (orders.isNullOrEmpty()) return emptyList()
        return orders.map { order ->
            val project = projectAdapter.queryById(order.projectId)
            val item = itemAdapter.queryItemByProjectId(project.id)
            val projectStatus = projectStatusAdapter.queryLatestByProjectId(project.id)
            OrderProjectsResponse.of(order.id, order.orderState, project, item.category, projectStatus)
        }
    }

    @Transactional(readOnly = true)
    fun findById(orderId: Long): OrderResponse {
        val order = orderAdapter.queryById(orderId)
        if (order.userId != SecurityUtils.currentUserId) throw OrderNotUserException.EXCEPTION
        val orderOptions = orderAdapter.findOrderOptionItemByOrderId(orderId)
        val orderAnswers = orderAdapter.findAllOrderAnswerByOrderIdOrNull(orderId)
        return OrderResponse.of(order, orderOptions, orderAnswers)
    }

    @Transactional(readOnly = true)
    fun findByStateAndName(
        projectId: Long,
        state: OrderState,
        name: String?,
        pageable: Pageable
    ): Page<OrderAdminResponse> {
        val project = projectAdapter.queryById(projectId)
        if (project.userId != SecurityUtils.currentUserId) throw ProjectNotHostException.EXCEPTION
        val orders = orderAdapter.queryOrders(project.id, state, name, pageable)
        return orders.map { order ->
            OrderAdminResponse.of(order)
        }
    }
}
