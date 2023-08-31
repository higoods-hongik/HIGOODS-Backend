package com.higoods.api.order.controller

import com.higoods.api.order.dto.response.OrderAdminResponse
import com.higoods.api.order.usecase.OrderApproveUseCase
import com.higoods.api.order.usecase.OrderCancelUseCase
import com.higoods.api.order.usecase.OrderReadUseCase
import com.higoods.domain.order.domain.OrderState
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admins/orders")
class OrderAdminController(
    private val orderCancelUseCase: OrderCancelUseCase,
    private val orderApproveUseCase: OrderApproveUseCase,
    private val orderReadUseCase: OrderReadUseCase
) {
    // 주문 취소
    @PostMapping("/{order_id}/cancellations")
    fun create(
        @PathVariable("order_id") orderId: Long
    ): Long {
        return orderCancelUseCase.execute(orderId)
    }

    // 입금 승인
    @PostMapping("/{order_id}/approvals")
    fun approve(
        @PathVariable("order_id") orderId: Long
    ): Long {
        return orderApproveUseCase.execute(orderId)
    }

    // 명단 관리-입금 확인 목록
    @GetMapping("/{project_id}")
    fun get(
        @PathVariable("project_id") projectId: Long,
        @RequestParam(value = "state", defaultValue = "PENDING") state: OrderState,
        @RequestParam(value = "name", required = false) name: String?,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Page<OrderAdminResponse> {
        // TODO: 페이지네이션 정상 작동 api 테스트하면서 다시 확인하기
        return orderReadUseCase.findByStateAndName(projectId, state, name, PageRequest.of(page, size))
    }
}
