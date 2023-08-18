package com.higoods.api.order.dto.request

import com.higoods.domain.order.domain.OrderOptionItem

data class OrderOptionDto(
    val optionId: Long,
    val count: Long
) {
    fun toOrderOptionItem(orderId: Long): OrderOptionItem {
        return OrderOptionItem(
            orderId = orderId,
            optionId = optionId,
            count = count
        )
    }

    companion object {
        fun of(optionItem: OrderOptionItem): OrderOptionDto {
            return OrderOptionDto(
                optionId = optionItem.optionId,
                count = optionItem.count
            )
        }
    }
}
