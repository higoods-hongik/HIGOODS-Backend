package com.higoods.api.distribution.dto.response

import com.higoods.common.helper.toHigoodsDateTimeFormat
import com.higoods.domain.distribution.domain.Distribution
import com.higoods.domain.distribution.domain.DistributionType
import com.higoods.domain.order.domain.Order

data class DistributionResponse(
    val distributionId: Long,
    val orderNo: String,
    val name: String,
    val orderDate: String,
    val phoneNumber: String,
    val receiveDate: String?,
    val distributionType: DistributionType
) {
    companion object {
        fun of(order: Order, distribution: Distribution): DistributionResponse {
            return DistributionResponse(
                distributionId = distribution.id,
                orderNo = order.orderNo,
                name = order.name,
                orderDate = order.createdAt.toHigoodsDateTimeFormat(),
                phoneNumber = order.phoneNum,
                receiveDate = distribution.receiveDate?.toHigoodsDateTimeFormat(),
                distributionType = distribution.distributionState

            )
        }
    }
}
