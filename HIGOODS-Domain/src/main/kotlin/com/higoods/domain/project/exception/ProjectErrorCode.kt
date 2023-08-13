package com.higoods.domain.project.exception

import com.higoods.common.const.BAD_REQUEST
import com.higoods.common.exception.BaseErrorCode
import com.higoods.common.exception.ExplainError
import com.higoods.common.exception.dto.ErrorReason
import java.util.*

enum class ProjectErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    @ExplainError("회원가입시에 이미 회원가입한 유저일시 발생하는 오류. 회원가입전엔 항상 register valid check 를 해주세요")
    PROJECT_NOT_FOUND(BAD_REQUEST, "USER_400_1", "이미 회원가입한 유저입니다.")
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
