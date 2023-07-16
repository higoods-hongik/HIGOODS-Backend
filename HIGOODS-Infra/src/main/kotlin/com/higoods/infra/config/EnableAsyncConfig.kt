package com.higoods.infra.config

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@Configuration
class EnableAsyncConfig(
    val customAsyncExceptionHandler: CustomAsyncExceptionHandler
) : AsyncConfigurer {
    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return customAsyncExceptionHandler
    }
}
