package com.higoods.api.test

import com.higoods.domain.test.entity.TestEntity
import com.higoods.domain.test.repository.TestQuerydslRepository
import com.higoods.domain.test.repository.TestRepository
import org.springframework.stereotype.Service

@Service
class TestService(
    private val testRepository: TestRepository,
    private val querydslRepository: TestQuerydslRepository
) {
    fun test(): TestResponse {
        val testEntity = TestEntity(null, "querydsl")
        testRepository.save(testEntity)
        testEntity.loggingTest()
        return TestResponse.of(querydslRepository.getTestByName("querydsl") ?: TestEntity())
    }
}
