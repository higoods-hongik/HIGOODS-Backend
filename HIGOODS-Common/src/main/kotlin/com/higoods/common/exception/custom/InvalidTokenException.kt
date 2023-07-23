package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class InvalidTokenException : HiGoodsCodeException(
    GlobalErrorCode.INVALID_TOKEN
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = InvalidTokenException()
    }
}
