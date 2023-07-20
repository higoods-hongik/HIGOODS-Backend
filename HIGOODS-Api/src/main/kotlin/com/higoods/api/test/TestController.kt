package com.higoods.api.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class TestController(
    private val testService: TestService
) {
    @GetMapping("/v1/test")
    fun getTest(): TestResponse {
        return testService.test("ASdfasdfa")
    }
}
