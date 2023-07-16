package com.higoods.common.exception.custom

import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException


class OtherServerForbiddenException : HiGoodsCodeException(
    GlobalErrorCode.OTHER_SERVER_FORBIDDEN,
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = OtherServerForbiddenException()
    }
}
