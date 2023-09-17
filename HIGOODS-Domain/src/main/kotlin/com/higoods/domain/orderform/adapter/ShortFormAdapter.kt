package com.higoods.domain.orderform.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.orderform.domain.ShortFormQuestion
import com.higoods.domain.orderform.repository.ShortFormRepository

@Adapter
class ShortFormAdapter(
    private val shortFormRepository: ShortFormRepository
) {

    fun save(shortFormQuestion: ShortFormQuestion): ShortFormQuestion {
        return shortFormRepository.save(shortFormQuestion)
    }

    fun findAllByOrderFormId(orderFormId: Long): List<ShortFormQuestion> {
        return shortFormRepository.findAllByOrderFormIdIn(orderFormId)
    }

    fun saveAll(shortFormQuestions: List<ShortFormQuestion>): List<ShortFormQuestion> {
        return shortFormRepository.saveAll(shortFormQuestions)
    }
}
