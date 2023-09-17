package com.higoods.api.orderform.usecase

import com.higoods.api.order.dto.request.OrderCreateRequest
import com.higoods.api.orderform.dto.response.OrderFormResponse
import com.higoods.common.annotation.UseCase

@UseCase
class OrderFormCreateUseCase {
    fun execute(projectId: Long, orderCreateRequest: OrderCreateRequest): OrderFormResponse {


    }
}