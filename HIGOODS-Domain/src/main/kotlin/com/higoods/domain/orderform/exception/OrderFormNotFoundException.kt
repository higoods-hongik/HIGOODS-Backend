package com.higoods.domain.orderform.exception

import com.higoods.common.exception.HiGoodsCodeException

class OrderFormNotFoundException : HiGoodsCodeException(
    OrderFormErrorCode.ORDER_FORM_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = OrderFormNotFoundException()
    }
}
