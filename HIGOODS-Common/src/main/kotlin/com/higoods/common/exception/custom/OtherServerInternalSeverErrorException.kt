package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class OtherServerInternalSeverErrorException : HiGoodsCodeException(
    GlobalErrorCode.OTHER_SERVER_INTERNAL_SERVER_ERROR,
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = OtherServerInternalSeverErrorException()
    }
}
