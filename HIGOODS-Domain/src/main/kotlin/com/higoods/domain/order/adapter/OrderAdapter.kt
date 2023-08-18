package com.higoods.domain.order.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.order.domain.Order
import com.higoods.domain.order.domain.OrderAnswer
import com.higoods.domain.order.domain.OrderOptionItem
import com.higoods.domain.order.repository.OrderAnswerRepository
import com.higoods.domain.order.repository.OrderOptionItemRepository
import com.higoods.domain.order.repository.OrderRepository

@Adapter
class OrderAdapter(
    private val orderRepository: OrderRepository,
    private val optionItemRepository: OrderOptionItemRepository,
    private val answerRepository: OrderAnswerRepository
) {
    fun saveOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    fun saveOrderOptionItem(optionItem: OrderOptionItem): OrderOptionItem {
        return optionItemRepository.save(optionItem)
    }

    fun saveOrderAnswer(answer: OrderAnswer): OrderAnswer {
        return answerRepository.save(answer)
    }
}
