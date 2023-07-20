package com.higoods.common.exception

class HiGoodsDynamicException(
    val status: Int,
    val code: String,
    val reason: String?
) : HiGoodsBaseException()
