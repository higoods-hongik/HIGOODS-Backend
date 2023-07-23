package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class NotAvailableRedissonLockException : HiGoodsCodeException(
    GlobalErrorCode.NOT_AVAILABLE_REDISSON_LOCK
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = NotAvailableRedissonLockException()
    }
}
