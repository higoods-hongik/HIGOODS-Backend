package com.higoods.api.order.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.order.dto.request.OrderCreateRequest
import com.higoods.api.order.dto.response.OrderResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.order.domain.OrderAnswer
import com.higoods.domain.order.service.OrderDomainService
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.service.ProjectDomainService

@UseCase
class OrderCreateUseCase(
    private val orderDomainService: OrderDomainService,
    private val projectAdapter: ProjectAdapter,
    private val projectDomainService: ProjectDomainService
) {
    fun execute(projectId: Long, orderCreateRequest: OrderCreateRequest): OrderResponse {
        val currentUserId = SecurityUtils.currentUserId
        val project = projectAdapter.queryById(projectId)
        // TODO: 리스폰스에 주문번호 업데이트 되는지 확인 필요
        val newOrder = orderDomainService.createOrder(orderCreateRequest.toOrder(project.id, currentUserId))
        val newOrderOptions = orderDomainService.createOrderOptions(orderCreateRequest.toOrderOptionItems(newOrder.id))
        var newOrderAnswers: List<OrderAnswer>? = null
        if (orderCreateRequest.orderOptions.isNotEmpty()) {
            newOrderAnswers = orderDomainService.createOrderAnswers(orderCreateRequest.toOrderOptionAnswers(newOrder.id))
        }
        projectDomainService.increasePurchaseNum(projectId) // 프로젝트 구매 인원 업데이트
        return OrderResponse.of(newOrder, newOrderOptions, newOrderAnswers)
    }
}
