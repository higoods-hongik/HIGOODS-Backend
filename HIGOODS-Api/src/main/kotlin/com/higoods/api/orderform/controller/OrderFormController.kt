package com.higoods.api.orderform.controller

import com.higoods.api.orderform.dto.request.OrderFormCreateRequest
import com.higoods.api.orderform.usecase.OrderFormCreateUseCase
import com.higoods.api.orderform.usecase.OrderFormReadUseCase
import com.higoods.domain.orderform.domain.OrderFormVo
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
        orderFormCreateRequests: List<OrderFormCreateRequest>
    ): OrderFormVo {
        return orderFormCreateUseCase.execute(projectId, orderFormCreateRequests)
    }

    /**
     * 주문폼은 한개만 관리합니다.
     */
    @GetMapping
    fun getOrderForm(
        @PathVariable("project_id") projectId: Long
    ): OrderFormVo {
        return orderFormReadUseCase.execute(projectId)
    }
}
