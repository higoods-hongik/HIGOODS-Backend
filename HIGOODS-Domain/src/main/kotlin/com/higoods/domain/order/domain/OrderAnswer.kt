package com.higoods.domain.order.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_order_answer")
class OrderAnswer(

    val orderId: Long, // 주문 아이디
    val optionId: Long, // 옵션 아이디

    val answerType: AnswerType, // 질문 타입
    val multipleChoiceId: Long = 0, // 객관식 아이디
    val shortAnswer: String = "" // 주관식 응답

) : BaseEntity()
