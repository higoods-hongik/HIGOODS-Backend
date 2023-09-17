package com.higoods.domain.orderform.repository

import com.higoods.domain.orderform.domain.OrderForm
import org.springframework.data.jpa.repository.JpaRepository

interface OrderFormRepository : JpaRepository<OrderForm, Long> {

    fun findByProjectId(projectId: Long): OrderForm?
}
