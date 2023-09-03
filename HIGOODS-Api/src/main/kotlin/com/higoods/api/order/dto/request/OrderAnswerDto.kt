package com.higoods.api.order.dto.request

import com.higoods.domain.order.domain.AnswerType
import com.higoods.domain.order.domain.OrderAnswer

data class OrderAnswerDto(
    val optionId: Long,
    val answerType: AnswerType, // 질문 타입
    val multipleChoiceId: Long = 0, // 객관식 아이디
    val shortAnswer: String = "" // 주관식 응답
) {
    fun toOrderAnswer(orderId: Long): OrderAnswer {
        return OrderAnswer(
            orderId = orderId,
            optionId = optionId,
            answerType = answerType,
            multipleChoiceId = multipleChoiceId,
            shortAnswer = shortAnswer
        )
    }

    companion object {
        fun of(answer: OrderAnswer): OrderAnswerDto {
            return OrderAnswerDto(
                optionId = answer.optionId,
                answerType = answer.answerType,
                multipleChoiceId = answer.multipleChoiceId,
                shortAnswer = answer.shortAnswer
            )
        }
    }
}
