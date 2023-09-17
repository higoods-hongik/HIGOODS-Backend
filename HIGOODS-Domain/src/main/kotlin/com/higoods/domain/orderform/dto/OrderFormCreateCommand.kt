package com.higoods.domain.orderform.dto

import com.higoods.domain.orderform.domain.OrderFormType

data class OrderFormCreateCommand(
    val type: OrderFormType,
    val question: String,
    val choices: List<String> = emptyList()
) {
    fun isMultipleChoice(): Boolean {
        return type == OrderFormType.MULTIPLE_CHOICE
    }

    fun isShortForm(): Boolean {
        return type == OrderFormType.SHORT_FORM
    }
}
