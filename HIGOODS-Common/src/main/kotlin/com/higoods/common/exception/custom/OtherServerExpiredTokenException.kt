package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class OtherServerExpiredTokenException : HiGoodsCodeException(
    GlobalErrorCode.OTHER_SERVER_EXPIRED_TOKEN,
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = OtherServerExpiredTokenException()
    }
}
