package com.higoods.api.orderform.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.orderform.dto.request.OrderFormCreateRequest
import com.higoods.common.annotation.UseCase
import com.higoods.domain.orderform.domain.OrderFormVo
import com.higoods.domain.orderform.service.OrderFormDomainService
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.exception.ProjectNotHostException

@UseCase
class OrderFormCreateUseCase(
    private val projectAdapter: ProjectAdapter,
    private val orderFormDomainService: OrderFormDomainService
) {
    fun execute(projectId: Long, orderCreateRequest: List<OrderFormCreateRequest>): OrderFormVo {
        val project = projectAdapter.queryById(projectId)
        if (project.userId != SecurityUtils.currentUserId) {
            throw ProjectNotHostException.EXCEPTION
        }
        return orderFormDomainService.create(projectId, orderCreateRequest.map { it.toCommand() })
    }
}
