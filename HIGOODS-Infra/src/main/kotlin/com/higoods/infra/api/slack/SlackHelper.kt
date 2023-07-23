package com.higoods.infra.api.slack

import com.higoods.common.annotation.Helper
import com.higoods.common.helper.SpringEnvironmentHelper
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.SlackApiException
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.model.block.LayoutBlock
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Helper
class SlackHelper(
    val springEnvironmentHelper: SpringEnvironmentHelper,
    val methodsClient: MethodsClient
) {
    val logger: Logger = LoggerFactory.getLogger(SlackHelper::class.java)
    fun sendNotification(channelId: String, layoutBlocks: List<LayoutBlock>) {
        if (!springEnvironmentHelper.isProdAndDevProfile) {
            return
        }
        val chatPostMessageRequest = ChatPostMessageRequest.builder()
            .channel(channelId)
            .blocks(layoutBlocks)
            .build()
        try {
            val chatPostMessage = methodsClient.chatPostMessage(chatPostMessageRequest)
            chatPostMessage.channel
        } catch (slackApiException: SlackApiException) {
            logger.error(slackApiException.response.toString())
        }
    }
}
