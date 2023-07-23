package com.higoods.domain.user.exception

import com.higoods.common.exception.HiGoodsCodeException

class UserNotFoundException : HiGoodsCodeException(
    UserErrorCode.USER_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = UserNotFoundException()
    }
}
