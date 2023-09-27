package com.higoods.domain.distribution.repository

import com.higoods.domain.distribution.domain.Distribution
import com.higoods.domain.distribution.domain.QDistribution.distribution
import com.higoods.domain.order.domain.QOrder.order
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import java.util.function.LongSupplier

@Repository
class DistributionQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findAllByProjectIdAndName(projectId: Long, name: String?, pageable: Pageable): Page<Distribution> {
        val distributions = queryFactory
            .selectFrom(distribution)
            .apply {
                if (name != null) {
                    this.innerJoin(order).on(distribution.orderId.eq(order.id).and(order.name.contains(name)))
                }
            }
            .where(distribution.projectId.eq(projectId))
            .orderBy(distribution.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = queryFactory.select(distribution.count())
            .from(distribution)
            .apply {
                if (name != null) {
                    this.innerJoin(order).on(distribution.orderId.eq(order.id).and(order.name.contains(name)))
                }
            }
            .where(distribution.projectId.eq(projectId))

        val countSupplier = LongSupplier { countQuery.fetchOne() ?: 0L }
        return PageableExecutionUtils.getPage(distributions, pageable, countSupplier)
    }
}
