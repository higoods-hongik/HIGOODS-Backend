package com.higoods.infra.config.slack

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "slack")
@ConstructorBinding
data class SlackProperties(
    val webhook: SlackSecret,
) {
    data class SlackSecret(
        val token: String,
        val url: String,
        val channelId: String,
        val userName: String,
    )
}
