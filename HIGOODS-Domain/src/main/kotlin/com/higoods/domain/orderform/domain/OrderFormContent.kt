package com.higoods.domain.orderform.domain

data class OrderFormContent(
    val type: OrderFormType,
    val question: String,
    val choices: List<MultipleChoiceContent> = emptyList()
)

data class MultipleChoiceContent(
    val id: Long,
    val optionDisplayName: String
)
