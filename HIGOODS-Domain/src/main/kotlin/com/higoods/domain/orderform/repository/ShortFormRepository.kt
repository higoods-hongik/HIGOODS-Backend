package com.higoods.domain.orderform.repository

import com.higoods.domain.orderform.domain.ShortFormQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface ShortFormRepository : JpaRepository<ShortFormQuestion, Long> {

    fun findAllByOrderFormIdIn(orderFormId: Long): List<ShortFormQuestion>
}
