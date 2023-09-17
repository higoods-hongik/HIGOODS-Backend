package com.higoods.domain.orderform.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.orderform.domain.MultipleChoiceGroup
import com.higoods.domain.orderform.repository.MultipleChoiceGroupRepository

@Adapter
class MultipleChoiceAdapter(
    private val multipleChoiceGroupRepository: MultipleChoiceGroupRepository
) {

    fun save(multipleChoiceGroup: MultipleChoiceGroup): MultipleChoiceGroup {
        return multipleChoiceGroupRepository.save(multipleChoiceGroup)
    }

    fun findAllByOrderFormId(orderFormId: Long): List<MultipleChoiceGroup> {
        return multipleChoiceGroupRepository.findAllByOrderFormIdIn(orderFormId)
    }
}
