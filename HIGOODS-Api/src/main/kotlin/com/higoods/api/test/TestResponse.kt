package com.higoods.api.test

import com.fasterxml.jackson.annotation.JsonFormat
import com.higoods.domain.test.entity.TestEntity
import java.time.LocalDate

data class TestResponse(
    var id: Long? = null,
    var name: String = "test",
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    var currentTime: LocalDate
) {
    companion object {
        fun of(testEntity: TestEntity): TestResponse {
            return TestResponse(testEntity.id, testEntity.name, testEntity.createdAt.toLocalDate())
        }
    }
}
