package com.higoods.domain.orderform.domain

data class OrderFormVo(
    val projectId: Long,
    val orderFormId: Long,
    val content: List<OrderFormContent>
)
