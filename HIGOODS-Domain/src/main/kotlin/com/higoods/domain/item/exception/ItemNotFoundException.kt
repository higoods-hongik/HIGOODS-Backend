package com.higoods.domain.item.exception

import com.higoods.common.exception.HiGoodsCodeException

class ItemNotFoundException : HiGoodsCodeException(
    ItemErrorCode.ITEM_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ItemNotFoundException()
    }
}
