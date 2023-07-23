package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class BadLockIdentifierException : HiGoodsCodeException(
    GlobalErrorCode.BAD_LOCK_IDENTIFIER
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = BadLockIdentifierException()
    }
}
