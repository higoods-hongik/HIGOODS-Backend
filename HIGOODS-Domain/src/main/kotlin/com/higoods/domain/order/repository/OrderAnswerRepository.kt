package com.higoods.domain.order.repository

import com.higoods.domain.order.domain.OrderAnswer
import org.springframework.data.jpa.repository.JpaRepository

interface OrderAnswerRepository : JpaRepository<OrderAnswer, Long> {
    fun findAllByOrderIdOrNull(orderId: Long): List<OrderAnswer>?
}
