package com.higoods.api.order.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.order.dto.response.OrderProjectsResponse
import com.higoods.api.order.dto.response.OrderResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.order.adapter.OrderAdapter
import com.higoods.domain.order.exception.OrderNotUserException
import com.higoods.domain.project.adapter.ProjectAdapter
import org.springframework.transaction.annotation.Transactional

@UseCase
class OrderReadUseCase(
    private val orderAdapter: OrderAdapter,
    private val projectAdapter: ProjectAdapter
) {
    @Transactional(readOnly = true)
    fun findAll(): List<OrderProjectsResponse> {
        val orders = orderAdapter.findAll(SecurityUtils.currentUserId)
        if (orders.isNullOrEmpty()) return emptyList()
        return orders.map { order ->
            val project = projectAdapter.queryById(order.projectId)
            OrderProjectsResponse.of(order.id, order.orderState, project)
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
}
