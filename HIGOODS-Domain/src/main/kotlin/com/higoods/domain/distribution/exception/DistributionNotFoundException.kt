package com.higoods.domain.distribution.exception

import com.higoods.common.exception.HiGoodsCodeException

class DistributionNotFoundException : HiGoodsCodeException(
    DistributionErrorCode.DISTRIBUTION_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = DistributionNotFoundException()
    }
}
