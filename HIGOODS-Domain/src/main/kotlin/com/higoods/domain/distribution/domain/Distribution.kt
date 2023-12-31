package com.higoods.domain.distribution.domain

import com.higoods.domain.common.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "tbl_distribution")
class Distribution(

    val projectId: Long, // 프로젝트 아이디
    val orderId: Long, // 주문 아이디
    val userId: Long, // 유저 아이디

    var receiveDate: LocalDateTime?, // 수령 날짜
    @Enumerated(EnumType.STRING)
    var distributionState: DistributionType = DistributionType.NOT_RECEIVED // 배부 상태

) : BaseEntity() {
    fun received() {
        this.distributionState = DistributionType.RECEIVED
        this.receiveDate = LocalDateTime.now()
    }

    fun notReceived() {
        this.distributionState = DistributionType.NOT_RECEIVED
        this.receiveDate = null
    }
}
