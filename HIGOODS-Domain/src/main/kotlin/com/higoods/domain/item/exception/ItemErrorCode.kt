package com.higoods.domain.item.exception

import com.higoods.common.const.NOT_FOUND
import com.higoods.common.exception.BaseErrorCode
import com.higoods.common.exception.ExplainError
import com.higoods.common.exception.dto.ErrorReason
import java.util.*

enum class ItemErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    @ExplainError("유저 정보를 찾을 수 없는 경우")
    ITEM_NOT_FOUND(NOT_FOUND, "ITEM_404_1", "아이템 정보를 찾을 수 없습니다.")

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
