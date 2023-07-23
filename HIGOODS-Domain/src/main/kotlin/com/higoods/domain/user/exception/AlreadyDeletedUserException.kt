package com.higoods.domain.user.exception

import com.higoods.common.exception.HiGoodsCodeException

class AlreadyDeletedUserException : HiGoodsCodeException(
    UserErrorCode.USER_ALREADY_DELETED
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = AlreadyDeletedUserException()
    }
}
