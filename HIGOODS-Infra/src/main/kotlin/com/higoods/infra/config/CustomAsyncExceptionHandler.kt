package com.higoods.infra.config

import com.higoods.infra.api.slack.SlackErrorSender
import mu.KotlinLogging
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
class CustomAsyncExceptionHandler(
    val slackErrorSender: SlackErrorSender
) : AsyncUncaughtExceptionHandler {

    private val logger = KotlinLogging.logger {}

    override fun handleUncaughtException(throwable: Throwable, method: Method, vararg params: Any?) {
        // Loger
        logger.error { method.name + throwable.message }
        for (param in params) {
            logger.error { param.toString() }
        }
        // Slack
        slackErrorSender.asyncError(method.name, throwable, params)
    }
}
