package com.higoods.api.order.dto.response

import com.higoods.api.order.dto.request.OrderAnswerDto
import com.higoods.api.order.dto.request.OrderOptionDto
import com.higoods.domain.order.domain.Order
import com.higoods.domain.order.domain.OrderAnswer
import com.higoods.domain.order.domain.OrderOptionItem
import com.higoods.domain.order.domain.ReceiveType

data class OrderResponse(
    val orderNo: String, // 주문 번호
    val name: String, // 이름
    val studentId: String, // 학번
    val phoneNum: String, // 전화번호
    val receiveType: ReceiveType, // 상품 수령 방법
    val depositName: String, // 입금자명
    val refundBank: String, // 환불 은행
    val refundAccount: String, // 환불 계좌
    val totalCost: Int,
    val orderOptions: List<OrderOptionDto>, // 상품 옵션 정보
    val orderAnswers: List<OrderAnswerDto>? // 커스텀 주문폼 정보
) {
    companion object {
        fun of(order: Order, optionItems: List<OrderOptionItem>, orderAnswers: List<OrderAnswer>?): OrderResponse {
            val options = optionItems.map {
                    orderOptionItem ->
                OrderOptionDto.of(orderOptionItem)
            }.toList()

            val answers = orderAnswers?.map { answer ->
                OrderAnswerDto.of(answer)
            }?.toList()

            return OrderResponse(
                orderNo = order.orderNo,
                name = order.name,
                studentId = order.studentId,
                phoneNum = order.phoneNum,
                receiveType = order.receiveType,
                depositName = order.depositName,
                refundBank = order.refundBank,
                refundAccount = order.refundAccount,
                totalCost = order.totalCost,
                orderOptions = options,
                orderAnswers = answers
            )
        }
    }
}
