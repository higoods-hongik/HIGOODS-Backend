package com.higoods.common.exception

import com.higoods.common.exception.dto.ErrorReason

interface BaseErrorCode {
    val errorReason: ErrorReason

    val explainError: String
}
