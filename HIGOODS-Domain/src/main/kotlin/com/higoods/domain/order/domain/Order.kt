package com.higoods.domain.order.domain

import com.higoods.common.const.NO_START_NUMBER
import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.PostPersist
import javax.persistence.Table

@Entity
@Table(name = "tbl_order")
class Order(

    val projectId: Long, // 프로젝트 아이디
    val userId: Long, // 유저 아이디

    var orderNo: String = "", // 주문 번호
    val name: String, // 이름
    val studentId: String, // 학번
    var phoneNum: String, // 전화번호
    @Enumerated(EnumType.STRING)
    var receiveType: ReceiveType, // 수령 방법
    var depositName: String, // 입금자명
    var refundBank: String, // 환불 은행
    var refundAccount: String, // 환불 계좌
    val totalCost: Int, // 총 금액
    @Enumerated(EnumType.STRING)
    var orderState: OrderState = OrderState.PENDING // 주문 상태

) : BaseEntity() {
    @PostPersist
    fun createOrder() {
        this.orderNo = "H" + (NO_START_NUMBER + id)
    }

    fun cancel() {
        this.orderState = OrderState.CANCELED
    }
}
