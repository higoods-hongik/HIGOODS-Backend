package com.higoods.api.order.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.common.annotation.UseCase
import com.higoods.domain.order.adapter.OrderAdapter
import com.higoods.domain.order.service.OrderDomainService
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.exception.ProjectNotHostException
import org.springframework.transaction.annotation.Transactional

@UseCase
class OrderApproveUseCase(
    private val orderDomainService: OrderDomainService,
    private val orderAdapter: OrderAdapter,
    private val projectAdapter: ProjectAdapter
) {
    @Transactional
    fun execute(orderId: Long): Long {
        val order = orderAdapter.queryById(orderId)
        val project = projectAdapter.queryById(order.projectId)
        if (project.userId != SecurityUtils.currentUserId) throw ProjectNotHostException.EXCEPTION
        return orderDomainService.approveOrder(order)
    }
}
