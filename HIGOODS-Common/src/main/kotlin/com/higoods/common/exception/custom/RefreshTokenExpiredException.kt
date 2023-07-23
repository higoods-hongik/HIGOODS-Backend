package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class RefreshTokenExpiredException : HiGoodsCodeException(
    GlobalErrorCode.REFRESH_TOKEN_EXPIRED
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = RefreshTokenExpiredException()
    }
}
