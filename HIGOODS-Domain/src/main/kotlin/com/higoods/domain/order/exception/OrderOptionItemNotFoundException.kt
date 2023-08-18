package com.higoods.domain.order.exception

import com.higoods.common.exception.HiGoodsCodeException
import com.higoods.domain.project.exception.ProjectNotFoundException

class OrderOptionItemNotFoundException : HiGoodsCodeException(
    OrderErrorCode.ORDER_OPTION_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ProjectNotFoundException()
    }
}
