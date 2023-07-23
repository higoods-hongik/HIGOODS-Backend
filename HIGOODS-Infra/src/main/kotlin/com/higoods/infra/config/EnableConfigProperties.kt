package com.higoods.infra.config

import com.higoods.infra.config.slack.SlackProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(SlackProperties::class, OauthProperties::class)
@Configuration
class EnableConfigProperties
