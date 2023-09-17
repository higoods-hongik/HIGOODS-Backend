package com.higoods.domain.orderform.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.orderform.adapter.MultipleChoiceAdapter
import com.higoods.domain.orderform.adapter.OrderFormAdapter
import com.higoods.domain.orderform.adapter.ShortFormAdapter

@DomainService
class OrderFormDomainService(
    private val orderFormAdapter: OrderFormAdapter,
    private val shortFormAdapter: ShortFormAdapter,
    private val multipleChoiceAdapter: MultipleChoiceAdapter
){


    fun
}
