package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class ExpiredTokenException : HiGoodsCodeException(
    GlobalErrorCode.TOKEN_EXPIRED
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ExpiredTokenException()
    }
}
