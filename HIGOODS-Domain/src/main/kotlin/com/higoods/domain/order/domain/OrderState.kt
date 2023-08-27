package com.higoods.domain.order.domain

enum class OrderState {
    PENDING, // 입금 대기
    APPROVAL, // 입금 확인
    CANCELED // 주문 취소
}
