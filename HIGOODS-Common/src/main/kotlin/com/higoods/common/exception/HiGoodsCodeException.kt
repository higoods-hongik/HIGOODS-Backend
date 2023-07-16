package com.higoods.common.exception

import com.higoods.common.exception.dto.ErrorReason

class HiGoodsCodeException(
    val errorCode: BaseErrorCode
) : HiGoodsBaseException() {
    val errorReason: ErrorReason
        get() {
            return errorCode.errorReason
        }
}
