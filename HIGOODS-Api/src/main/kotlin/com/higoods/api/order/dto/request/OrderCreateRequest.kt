package com.higoods.api.order.dto.request

import com.higoods.domain.order.domain.Order
import com.higoods.domain.order.domain.OrderAnswer
import com.higoods.domain.order.domain.OrderOptionItem
import com.higoods.domain.order.domain.OrderState
import com.higoods.domain.order.domain.ReceiveType

data class OrderCreateRequest(
    val name: String, // 이름
    val studentId: String, // 학번
    val phoneNum: String, // 전화번호
    val receiveType: ReceiveType, // 상품 수령 방법
    val depositName: String, // 입금자명
    val refundBank: String, // 환불 은행
    val refundAccount: String, // 환불 계좌
    val totalCost: Int, // 총 금액
    val orderOptions: List<OrderOptionDto>, // 상품 옵션 정보
    val orderAnswers: List<OrderAnswerDto>? // 커스텀 주문폼 정보
) {
    fun toOrder(projectId: Long, userId: Long): Order {
        return Order(
            projectId = projectId,
            userId = userId,
            name = name,
            studentId = studentId,
            phoneNum = phoneNum,
            receiveType = receiveType,
            depositName = depositName,
            refundBank = refundBank,
            refundAccount = refundAccount,
            totalCost = totalCost,
            orderState = OrderState.PENDING
        )
    }

    fun toOrderOptionItems(orderId: Long): List<OrderOptionItem> {
        return orderOptions
            .map {
                    orderOptionDto ->
                orderOptionDto.toOrderOptionItem(orderId)
            }
            .toList()
    }

    fun toOrderOptionAnswers(orderId: Long): List<OrderAnswer> {
        return orderAnswers!!
            .map {
                    orderAnswerDto ->
                orderAnswerDto.toOrderAnswer(orderId)
            }
            .toList()
    }
}
