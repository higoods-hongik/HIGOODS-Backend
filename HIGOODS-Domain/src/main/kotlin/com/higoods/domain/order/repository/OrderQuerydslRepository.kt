package com.higoods.domain.order.repository

import com.higoods.domain.order.domain.Order
import com.higoods.domain.order.domain.OrderState
import com.higoods.domain.order.domain.QOrder.order
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import java.util.function.LongSupplier

@Repository
class OrderQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findAllByProjectIdAndStateAndName(projectId: Long, state: OrderState, name: String?, pageable: Pageable): Page<Order> {
        val orders = queryFactory.select(order)
            .from(order)
            .apply {
                if (name != null) {
                    this.where(order.name.contains(name))
                }
            }
            .where(order.projectId.eq(projectId), order.orderState.eq(state))
            .orderBy(order.orderNo.asc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
        val countQuery = queryFactory.select(order.count())
            .from(order)
            .apply {
                if (name != null) {
                    this.where(order.name.contains(name))
                }
            }
            .where(order.projectId.eq(projectId), order.orderState.eq(state))
        val countSupplier = LongSupplier { countQuery.fetchOne() ?: 0L }
        return PageableExecutionUtils.getPage(orders, pageable, countSupplier)
    }
}
