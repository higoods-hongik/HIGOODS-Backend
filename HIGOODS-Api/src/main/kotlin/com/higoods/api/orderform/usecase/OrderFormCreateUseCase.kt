package com.higoods.api.orderform.usecase

import com.higoods.api.orderform.dto.request.OrderFormCreateRequest
import com.higoods.common.annotation.UseCase
import com.higoods.domain.orderform.domain.OrderFormVo
import com.higoods.domain.orderform.service.OrderFormDomainService

@UseCase
class OrderFormCreateUseCase(
    private val orderFormDomainService: OrderFormDomainService
) {
    fun execute(projectId: Long, orderCreateRequest: List<OrderFormCreateRequest>): OrderFormVo {
        return orderFormDomainService.create(projectId, orderCreateRequest.map { it.toCommand() })
    }
}
