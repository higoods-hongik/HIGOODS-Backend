package com.higoods.domain.user.exception

import com.higoods.common.exception.HiGoodsCodeException

class AlreadySignUpUserException : HiGoodsCodeException(
    UserErrorCode.USER_ALREADY_SIGNUP
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = AlreadySignUpUserException()
    }
}
