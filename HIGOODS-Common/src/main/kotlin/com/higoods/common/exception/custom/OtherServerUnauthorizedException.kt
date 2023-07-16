package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class OtherServerUnauthorizedException : HiGoodsCodeException(
    GlobalErrorCode.OTHER_SERVER_UNAUTHORIZED
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = OtherServerUnauthorizedException()
    }
}
