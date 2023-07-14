package com.higoods.domain.test.repository

import com.higoods.domain.test.entity.QTestEntity.testEntity
import com.higoods.domain.test.entity.TestEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class TestQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun getTestByName(name: String): TestEntity? {
        return queryFactory.select(testEntity)
            .from(testEntity)
            .where(testEntity.name.eq(name))
            .limit(1)
            .fetchOne()
    }
}
