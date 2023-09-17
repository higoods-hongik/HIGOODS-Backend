package com.higoods.api.orderform.controller

import com.higoods.api.order.dto.request.OrderCreateRequest
import com.higoods.api.orderform.dto.response.OrderFormResponse
import com.higoods.api.orderform.usecase.OrderFormCreateUseCase
import com.higoods.api.orderform.usecase.OrderFormReadUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/project/{project_id}/order-form")
class OrderFormController(
    private val orderFormCreateUseCase: OrderFormCreateUseCase,
    private val orderFormReadUseCase: OrderFormReadUseCase
) {

    @PostMapping
    fun create(
        @PathVariable("project_id") projectId: Long,
        @Validated @RequestBody
        orderCreateRequest: OrderCreateRequest
    ): OrderFormResponse {
        return orderFormCreateUseCase.execute(projectId, orderCreateRequest)
    }

    /**
     * 주문폼은 한개만 관리합니다.
     */
    @GetMapping
    fun getOrderForm(
        @PathVariable("project_id") projectId: Long
    ): OrderFormResponse {
        return orderFormReadUseCase.execute(projectId)
    }
}
