package com.higoods.domain.orderform.exception

import com.higoods.common.const.NOT_FOUND
import com.higoods.common.exception.BaseErrorCode
import com.higoods.common.exception.ExplainError
import com.higoods.common.exception.dto.ErrorReason
import java.util.*

enum class OrderFormErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    ORDER_FORM_NOT_FOUND(NOT_FOUND, "ORDER_FORM_404_1", "주문 폼을 찾을 수 없습니다.")
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
