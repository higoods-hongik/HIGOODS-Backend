package com.higoods.domain.order.exception

import com.higoods.common.const.BAD_REQUEST
import com.higoods.common.const.NOT_FOUND
import com.higoods.common.exception.BaseErrorCode
import com.higoods.common.exception.ExplainError
import com.higoods.common.exception.dto.ErrorReason
import java.util.*

enum class OrderErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {
    ORDER_NOT_FOUND(NOT_FOUND, "ORDER_404_1", "주문을 찾을 수 없습니다."),
    ORDER_NOT_USER(BAD_REQUEST, "ORDER_400_1", "현재 유저가 주문한 주문이 아닙니다."),
    ORDER_OPTION_NOT_FOUND(NOT_FOUND, "ORDER_404_2", "주문 옵션을 찾을 수 없습니다.")
    ;
    override val errorReason: ErrorReason
        get() { return ErrorReason(status, code, reason) }

    override val explainError: String
        get() {
            val field = this.javaClass.getField(name)
            val annotation = field.getAnnotation(ExplainError::class.java)
            return if (Objects.nonNull(annotation)) annotation.value else reason
        }
}
