package com.higoods.domain.order.exception

import com.higoods.common.exception.HiGoodsCodeException
import com.higoods.domain.project.exception.ProjectNotHostException

class OrderNotUserException : HiGoodsCodeException(
    OrderErrorCode.ORDER_NOT_USER
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ProjectNotHostException()
    }
}
