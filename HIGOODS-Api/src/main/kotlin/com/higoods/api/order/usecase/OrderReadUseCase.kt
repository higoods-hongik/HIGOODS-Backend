package com.higoods.api.order.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.order.dto.response.OrderResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.order.adapter.OrderAdapter
import com.higoods.domain.order.exception.OrderNotUserException
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.user.adapter.UserAdapter

@UseCase
class OrderReadUseCase(
    private val orderAdapter: OrderAdapter,
    private val projectAdapter: ProjectAdapter,
    private val userAdapter: UserAdapter
) {
//    fun findAll(): List<OrderProjectsResponse> {
//        val findAll = orderAdapter.findAll(SecurityUtils.currentUserId)
//        val userIds = findAll.map { project ->
//            project.userId
//        }.toList()
//        val users = userAdapter.queryUsers(userIds)
//        return findAll.map { project ->
//            val user = users.find { it.id == project.userId } ?: throw UserNotFoundException.EXCEPTION
//            ProjectResponse.of(project, user.toUserInfoVo())
//        }
//    }

    fun findById(orderId: Long): OrderResponse {
        val order = orderAdapter.queryById(orderId)
        if (order.userId != SecurityUtils.currentUserId) throw OrderNotUserException.EXCEPTION
        val orderOptions = orderAdapter.findOrderOptionItemByOrderId(orderId)
        val orderAnswers = orderAdapter.findAllOrderAnswerByOrderIdOrNull(orderId)
        return OrderResponse.of(order, orderOptions, orderAnswers)
    }
}
