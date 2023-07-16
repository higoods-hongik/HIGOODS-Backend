package com.higoods.infra.api.slack

import com.higoods.common.annotation.Helper
import com.higoods.common.const.SLACK_MAX_LENGTH
import com.higoods.infra.config.slack.SlackProperties
import com.slack.api.model.block.LayoutBlock
import org.springframework.scheduling.annotation.Async
import java.util.Arrays

@Helper
class SlackErrorNotificationProvider(
    val slackHelper: SlackHelper,
    val slackProperties: SlackProperties,
) {
    var slackWebHook: SlackProperties.SlackSecret = slackProperties.webhook

    fun getErrorStack(throwable: Throwable): String {
        val exceptionAsStrings = Arrays.toString(throwable.stackTrace)
        val cutLength = Math.min(exceptionAsStrings.length, SLACK_MAX_LENGTH)
        return exceptionAsStrings.substring(0, cutLength)
    }

    @Async
    fun sendNotification(layoutBlocks: List<LayoutBlock>) {
        slackHelper.sendNotification(slackWebHook.url, slackWebHook.token, slackWebHook.channelId, layoutBlocks)
    }
}
