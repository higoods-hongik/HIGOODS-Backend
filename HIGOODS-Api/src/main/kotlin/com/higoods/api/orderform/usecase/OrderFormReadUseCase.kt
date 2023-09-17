package com.higoods.api.orderform.usecase

import com.higoods.common.annotation.UseCase
import com.higoods.domain.orderform.adapter.MultipleChoiceAdapter
import com.higoods.domain.orderform.adapter.OrderFormAdapter
import com.higoods.domain.orderform.adapter.ShortFormAdapter
import com.higoods.domain.orderform.domain.OrderFormVo

@UseCase
class OrderFormReadUseCase(
    private val orderFormAdapter: OrderFormAdapter,
    private val shortFormAdapter: ShortFormAdapter,
    private val multipleChoiceAdapter: MultipleChoiceAdapter
) {
    fun execute(projectId: Long): OrderFormVo {
        val orderForm = orderFormAdapter.findByProjectId(projectId)

        val shortFormQuestions = shortFormAdapter.findAllByOrderFormId(orderForm.id)
        val multipleChoiceGroups = multipleChoiceAdapter.findAllByOrderFormId(orderForm.id)

        return OrderFormVo(
            projectId = projectId,
            orderFormId = orderForm.id,
            content = multipleChoiceGroups.map { it.toOrderFormContent() } +
                shortFormQuestions.map { it.toOrderFormContent() }
        )
    }
}
