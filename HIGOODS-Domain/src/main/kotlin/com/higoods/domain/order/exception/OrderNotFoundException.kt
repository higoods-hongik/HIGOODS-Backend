package com.higoods.domain.order.exception

import com.higoods.common.exception.HiGoodsCodeException
import com.higoods.domain.project.exception.ProjectNotFoundException

class OrderNotFoundException : HiGoodsCodeException(
    OrderErrorCode.ORDER_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ProjectNotFoundException()
    }
}
