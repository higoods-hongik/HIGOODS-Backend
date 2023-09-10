package com.higoods.api.item.dto.request

data class ItemOptionGroupRequest(
    val name: String,
    val itemOptions: List<ItemOptionRequest>
)
