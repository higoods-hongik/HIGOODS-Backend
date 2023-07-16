package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException

class OtherServerBadRequestException : HiGoodsCodeException(
    GlobalErrorCode.OTHER_SERVER_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = OtherServerBadRequestException()
    }
}
