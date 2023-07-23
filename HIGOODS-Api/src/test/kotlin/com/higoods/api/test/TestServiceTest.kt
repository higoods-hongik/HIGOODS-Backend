package com.higoods.api.test

import com.higoods.domain.test.entity.TestEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class TestServiceTest : StringSpec({
    val testService: TestService = mockk()

    "kotest 테스트 " {
        // given
        every { testService.test("test") } returns TestResponse.of(TestEntity())
        val result = testService.test("test")

        result.name shouldBe "test"
    }
})
