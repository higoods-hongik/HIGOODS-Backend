package com.higoods.api.order.controller

import com.higoods.api.order.usecase.OrderCancelUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admins/orders")
class OrderAdminController(
    private val orderCancelUseCase: OrderCancelUseCase
) {
    // 주문 취소
    @PostMapping("/{order_id}")
    fun create(
        @PathVariable("order_id") orderId: Long
    ): Long {
        return orderCancelUseCase.execute(orderId)
    }

    @PostMapping("/{order_id}/approvals")
    fun approve(
        @PathVariable("order_id") orderId: Long
    ): Long {
        return orderCancelUseCase.execute(orderId)
    }
}
