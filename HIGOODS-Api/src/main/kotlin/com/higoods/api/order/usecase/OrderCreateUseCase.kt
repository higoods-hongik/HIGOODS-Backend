package com.higoods.api.order.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.order.dto.request.OrderCreateRequest
import com.higoods.api.order.dto.response.OrderResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.distribution.service.DistributionDomainService
import com.higoods.domain.order.domain.OrderAnswer
import com.higoods.domain.order.service.OrderDomainService
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.service.ProjectDomainService
import org.springframework.transaction.annotation.Transactional

@UseCase
class OrderCreateUseCase(
    private val orderDomainService: OrderDomainService,
    private val projectAdapter: ProjectAdapter,
    private val projectDomainService: ProjectDomainService,
    private val distributionDomainService: DistributionDomainService
) {
    @Transactional
    fun execute(projectId: Long, orderCreateRequest: OrderCreateRequest): OrderResponse {
        val currentUserId = SecurityUtils.currentUserId
        val project = projectAdapter.queryById(projectId)
        val newOrder = orderDomainService.createOrder(orderCreateRequest.toOrder(project.id, currentUserId))
        val newOrderOptions = orderDomainService.createOrderOptions(orderCreateRequest.toOrderOptionItems(newOrder.id))
        var newOrderAnswers: List<OrderAnswer>? = null
        if (orderCreateRequest.orderOptions.isNotEmpty()) {
            newOrderAnswers = orderDomainService.createOrderAnswers(orderCreateRequest.toOrderOptionAnswers(newOrder.id))
        }
        projectDomainService.increasePurchaseNum(projectId) // 프로젝트 구매 인원 업데이트
        distributionDomainService.createDistribution(projectId, newOrder.id, currentUserId)

        return OrderResponse.of(newOrder, newOrderOptions, newOrderAnswers)
    }
}
