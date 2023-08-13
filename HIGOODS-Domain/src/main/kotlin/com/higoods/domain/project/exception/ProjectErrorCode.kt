package com.higoods.domain.project.exception

import com.higoods.common.const.BAD_REQUEST
import com.higoods.common.const.NOT_FOUND
import com.higoods.common.exception.BaseErrorCode
import com.higoods.common.exception.ExplainError
import com.higoods.common.exception.dto.ErrorReason
import java.util.*

enum class ProjectErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    PROJECT_NOT_FOUND(NOT_FOUND, "PROJECT_404_1", "프로젝트를 찾을 수 없습니다."),
    PROJECT_NOT_HOST(BAD_REQUEST, "PROJECT_400_1", "프로젝트에 권한이 없는 유저입니다.")
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
