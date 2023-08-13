package com.higoods.domain.order.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "tbl_order")
class Order(

    val projectId: Long, // 프로젝트 아이디
    val userId: Long, // 유저 아이디

    val name: String, // 이름
    val studentId: String, // 학번
    var phoneNum: String, // 전화번호
    @Enumerated(EnumType.STRING)
    var receiveType: ReceiveType, // 수령 방법
    var refundAccount: String, // 환불 계좌
    @Enumerated(EnumType.STRING)
    var orderState: OrderState // 주문 상태

) : BaseEntity()
