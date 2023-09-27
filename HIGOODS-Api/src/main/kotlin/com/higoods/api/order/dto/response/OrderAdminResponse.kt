package com.higoods.api.order.dto.response

import com.higoods.common.helper.toHigoodsDateTimeFormat
import com.higoods.domain.order.domain.Order
import com.higoods.domain.order.domain.OrderState

data class OrderAdminResponse(
    val orderId: Long,
    val orderNo: String, // 주문 번호
    val name: String, // 이름
    val depositName: String, // 입금자명
    val createdAt: String, // 주문 일시
    val phoneNum: String, // 전화번호
    val totalCost: Int, // 총 금액
    val orderState: OrderState // 주문 상태
) {
    companion object {
        fun of(order: Order): OrderAdminResponse {
            return OrderAdminResponse(
                orderId = order.id,
                orderNo = order.orderNo,
                name = order.name,
                depositName = order.depositName,
                createdAt = order.createdAt.toHigoodsDateTimeFormat(),
                phoneNum = order.phoneNum,
                totalCost = order.totalCost,
                orderState = order.orderState
            )
        }
    }
}
