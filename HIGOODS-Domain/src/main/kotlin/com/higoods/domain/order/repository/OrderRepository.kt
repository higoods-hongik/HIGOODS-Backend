package com.higoods.domain.order.repository

import com.higoods.domain.order.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>
