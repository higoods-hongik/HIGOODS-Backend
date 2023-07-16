package com.higoods.infra.api.slack.config

import com.higoods.infra.config.slack.SlackProperties
import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackApiConfig(
    val slackProperties: SlackProperties,
) {

    @Bean
    fun slackClient() : MethodsClient {
        val slackClient = Slack.getInstance()
        return slackClient.methods(slackProperties.webhook.token)
    }
}
