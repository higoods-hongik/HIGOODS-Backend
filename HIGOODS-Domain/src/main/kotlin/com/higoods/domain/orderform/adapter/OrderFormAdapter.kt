package com.higoods.domain.orderform.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.orderform.domain.OrderForm
import com.higoods.domain.orderform.repository.OrderFormRepository

@Adapter
class OrderFormAdapter(
    private val orderFormRepository: OrderFormRepository
) {

    fun upsert(orderForm: OrderForm): OrderForm {
        return orderFormRepository.findByProjectId(orderForm.projectId) ?: orderFormRepository.save(orderForm)
    }
}
