package com.higoods.api.order.controller

import com.higoods.api.order.dto.request.OrderCreateRequest
import com.higoods.api.order.dto.response.OrderProjectsResponse
import com.higoods.api.order.dto.response.OrderResponse
import com.higoods.api.order.usecase.OrderCreateUseCase
import com.higoods.api.order.usecase.OrderReadUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val orderCreateUseCase: OrderCreateUseCase,
    private val orderReadUseCase: OrderReadUseCase
) {
    // 주문 생성
    @PostMapping("/{project_id}")
    fun create(
        @PathVariable("project_id") projectId: Long,
        @Validated @RequestBody
        orderCreateRequest: OrderCreateRequest
    ): OrderResponse {
        return orderCreateUseCase.execute(projectId, orderCreateRequest)
    }

    // 마이페이지-내 주문 목록 조회
    @GetMapping
    fun findAll(): List<OrderProjectsResponse> {
        return orderReadUseCase.findAll()
    }

    // 내 주문 상세 조회
    @GetMapping("/{order_id}")
    fun findOrderById(
        @PathVariable("order_id") orderId: Long
    ): OrderResponse {
        return orderReadUseCase.findById(orderId)
    }
}
