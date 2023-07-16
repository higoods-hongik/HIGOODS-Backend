package com.higoods.infra.api.slack.config

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackApiConfig(
    val slackProperties: SlackProperties,
) {
    var slackWebHook: SlackProperties.SlackSecret = slackProperties.webhook

    @get:Bean
    val client: MethodsClient
        get() {
            val slackClient = Slack.getInstance()
            return slackClient.methods(slackWebHook.token)
        }
}
