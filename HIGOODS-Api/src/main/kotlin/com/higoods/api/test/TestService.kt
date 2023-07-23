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

    fun test(hi: String): TestResponse {
        val testEntity = TestEntity("querydsl")
        testRepository.save(testEntity)
        testEntity.loggingTest()
        return TestResponse.of(querydslRepository.getTestByName("querydsl") ?: TestEntity())
    }
}
