package com.higoods.api.test

import com.higoods.domain.test.entity.TestEntity
import com.higoods.domain.test.entity.repository.TestRepository
import org.springframework.stereotype.Service

@Service
class TestService(
    private val testRepository: TestRepository
) {
    fun test(): TestResponse {
        val testEntity = TestEntity(null, "querydsl")
        testRepository.save(testEntity)
        testEntity.loggingTest()
        return TestResponse.of(testEntity)
    }
}
