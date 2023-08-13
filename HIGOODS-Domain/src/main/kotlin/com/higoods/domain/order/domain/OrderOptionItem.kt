package com.higoods.domain.order.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_order_item")
class OrderOptionItem(

    val orderId: Long, // 주문 아이디
    val optionId: Long, // 옵션 아이디

    var count: Long // 수량

) : BaseEntity()
