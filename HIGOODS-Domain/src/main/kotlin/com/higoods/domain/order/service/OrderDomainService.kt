package com.higoods.domain.order.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.common.lock.RedissonLock
import com.higoods.domain.order.adapter.OrderAdapter
import com.higoods.domain.order.domain.Order
import com.higoods.domain.order.domain.OrderAnswer
import com.higoods.domain.order.domain.OrderOptionItem

@DomainService
class OrderDomainService(
    private val orderAdapter: OrderAdapter
) {
    // TODO: 클래스 key 값 잘 들어가는지 확인 필요
    @RedissonLock(key = "#order.projectId", lockName = "주문 생성")
    fun createOrder(order: Order): Order {
        return orderAdapter.saveOrder(order)
    }

    @RedissonLock(key = "#orderOptionItems.get(0).orderId", lockName = "주문 옵션 생성")
    fun createOrderOptions(orderOptionItems: List<OrderOptionItem>): List<OrderOptionItem> {
        return orderOptionItems.map {
                orderOptionItem ->
            orderAdapter.saveOrderOptionItem(orderOptionItem)
        }
            .toList()
    }

    @RedissonLock(key = "#answers.get(0).orderId", lockName = "주문폼 답변 생성")
    fun createOrderAnswers(answers: List<OrderAnswer>): List<OrderAnswer> {
        return answers.map {
                answer ->
            orderAdapter.saveOrderAnswer(answer)
        }
            .toList()
    }

    fun cancelOrder(order: Order): Order {
        order.cancel()
        return order
    }

    fun approveOrder(order: Order): Order {
        order.approve()
        return order
    }
}
