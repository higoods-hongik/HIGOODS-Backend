package com.higoods.domain.orderform.repository

import com.higoods.domain.orderform.domain.MultipleChoiceGroup
import org.springframework.data.jpa.repository.JpaRepository

interface MultipleChoiceGroupRepository : JpaRepository<MultipleChoiceGroup, Long> {

    fun findAllByOrderFormIdIn(orderFormId: Long): List<MultipleChoiceGroup>
}
