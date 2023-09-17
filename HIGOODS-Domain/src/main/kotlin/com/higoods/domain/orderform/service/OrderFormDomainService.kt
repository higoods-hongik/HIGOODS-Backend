package com.higoods.domain.orderform.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.orderform.adapter.MultipleChoiceAdapter
import com.higoods.domain.orderform.adapter.OrderFormAdapter
import com.higoods.domain.orderform.adapter.ShortFormAdapter
import com.higoods.domain.orderform.domain.MultipleChoiceGroup
import com.higoods.domain.orderform.domain.MultipleChoiceQuestion
import com.higoods.domain.orderform.domain.OrderForm
import com.higoods.domain.orderform.domain.OrderFormVo
import com.higoods.domain.orderform.domain.ShortFormQuestion
import com.higoods.domain.orderform.dto.OrderFormCreateCommand

@DomainService
class OrderFormDomainService(
    private val orderFormAdapter: OrderFormAdapter,
    private val shortFormAdapter: ShortFormAdapter,
    private val multipleChoiceAdapter: MultipleChoiceAdapter
) {

    fun create(projectId: Long, commands: List<OrderFormCreateCommand>): OrderFormVo {
        val orderFormEntity = orderFormAdapter.upsert(OrderForm(projectId))

        val multipleChoiceForms = saveMultipleChoiceForm(commands, orderFormEntity)

        val saveShortFormQuestions = saveShortFormQuestions(commands, orderFormEntity)

        return OrderFormVo(
            projectId = projectId,
            orderFormId = orderFormEntity.id,
            content = multipleChoiceForms.map { it.toOrderFormContent() } +
                saveShortFormQuestions.map { it.toOrderFormContent() }
        )
    }

    private fun saveMultipleChoiceForm(
        commands: List<OrderFormCreateCommand>,
        orderFormEntity: OrderForm
    ): List<MultipleChoiceGroup> {
        val multipleChoiceCommands = commands.filter { it.isMultipleChoice() }
        return multipleChoiceAdapter.saveAll(
            multipleChoiceCommands.map {
                MultipleChoiceGroup(
                    orderFormId = orderFormEntity.id,
                    choices = it.choices.map { choices -> MultipleChoiceQuestion(choices) },
                    question = it.question
                )
            }
        )
    }

    private fun saveShortFormQuestions(
        commands: List<OrderFormCreateCommand>,
        orderFormEntity: OrderForm
    ): List<ShortFormQuestion> {
        val shortFormCommands = commands.filter { it.isShortForm() }
        return shortFormAdapter.saveAll(
            shortFormCommands.map {
                ShortFormQuestion(
                    orderFormId = orderFormEntity.id,
                    question = it.question
                )
            }
        )
    }
}
