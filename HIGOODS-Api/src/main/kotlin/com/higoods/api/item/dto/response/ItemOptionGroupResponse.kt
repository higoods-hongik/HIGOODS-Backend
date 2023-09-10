package com.higoods.api.item.dto.response

data class ItemOptionGroupResponse(
    val name: String,
    val id: Long,
    val itemOptions: List<ItemOptionResponse>
)
