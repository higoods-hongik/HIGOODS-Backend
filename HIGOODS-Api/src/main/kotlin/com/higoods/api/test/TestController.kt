package com.higoods.api.test

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController(
    private val testService: TestService
) {
    private val log = KLogging().logger

    @GetMapping("/v1/test")
    fun getTest(): TestResponse {
        return testService.test()
    }
}
