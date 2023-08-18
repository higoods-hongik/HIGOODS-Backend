package com.higoods.domain.order.repository

import com.higoods.domain.order.domain.OrderOptionItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderOptionItemRepository : JpaRepository<OrderOptionItem, Long> {
    fun findAllByOrderIdOrNull(orderId: Long): List<OrderOptionItem>?
}
