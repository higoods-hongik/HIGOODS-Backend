package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException


class SecurityContextNotFoundException : HiGoodsCodeException(
    GlobalErrorCode.SECURITY_CONTEXT_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = SecurityContextNotFoundException()
    }
}
