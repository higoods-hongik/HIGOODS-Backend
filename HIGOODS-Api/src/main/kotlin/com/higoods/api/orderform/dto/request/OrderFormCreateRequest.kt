package com.higoods.api.orderform.dto.request

import com.higoods.domain.orderform.domain.OrderFormType
import com.higoods.domain.orderform.dto.OrderFormCreateCommand

data class OrderFormCreateRequest(
    val type: OrderFormType,
    val question: String,
    val choices: List<String> = emptyList()
) {
    fun toCommand(): OrderFormCreateCommand {
        return OrderFormCreateCommand(
            type = type,
            question = question,
            choices = choices
        )
    }
}
