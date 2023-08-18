package com.higoods.api.order.controller

import com.higoods.api.order.dto.request.OrderCreateRequest
import com.higoods.api.order.dto.response.OrderResponse
import com.higoods.api.order.usecase.OrderCreateUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val orderCreateUseCase: OrderCreateUseCase
) {
    @PostMapping("/{project_id}")
    fun create(
        @PathVariable("project_id") projectId: Long,
        @Validated @RequestBody
        orderCreateRequest: OrderCreateRequest
    ): OrderResponse {
        return orderCreateUseCase.execute(projectId, orderCreateRequest)
    }
}
