package com.higoods.domain.orderform.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_order_form")
class OrderForm(

    val projectId: Long

) : BaseEntity()
