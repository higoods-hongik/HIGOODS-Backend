package com.higoods.domain.user.exception

import com.higoods.common.exception.HiGoodsCodeException

class ForbiddenUserException : HiGoodsCodeException(
    UserErrorCode.USER_FORBIDDEN
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ForbiddenUserException()
    }
}
