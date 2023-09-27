package com.higoods.domain.order.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.order.domain.Order
import com.higoods.domain.order.domain.OrderAnswer
import com.higoods.domain.order.domain.OrderOptionItem
import com.higoods.domain.order.domain.OrderState
import com.higoods.domain.order.exception.OrderNotFoundException
import com.higoods.domain.order.exception.OrderOptionItemNotFoundException
import com.higoods.domain.order.repository.OrderAnswerRepository
import com.higoods.domain.order.repository.OrderOptionItemRepository
import com.higoods.domain.order.repository.OrderQuerydslRepository
import com.higoods.domain.order.repository.OrderRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull

@Adapter
class OrderAdapter(
    private val orderRepository: OrderRepository,
    private val orderQuerydslRepository: OrderQuerydslRepository,
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

    fun queryById(orderId: Long): Order {
        return orderRepository.findByIdOrNull(orderId) ?: throw OrderNotFoundException.EXCEPTION
    }

    fun findOrderOptionItemByOrderId(orderId: Long): List<OrderOptionItem> {
        return optionItemRepository.findAllByOrderId(orderId) ?: throw OrderOptionItemNotFoundException.EXCEPTION
    }

    fun findAllOrderAnswerByOrderIdOrNull(orderId: Long): List<OrderAnswer>? {
        return answerRepository.findAllByOrderId(orderId)
    }

    fun findAll(userId: Long): List<Order>? {
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
    }

    fun queryOrders(projectId: Long, state: OrderState, name: String?, pageable: Pageable): Page<Order> {
        return orderQuerydslRepository.findAllByProjectIdAndStateAndName(projectId, state, name, pageable)
    }

    fun findAllById(orderIds: List<Long>): List<Order> {
        return orderRepository.findAllById(orderIds)
    }
}
