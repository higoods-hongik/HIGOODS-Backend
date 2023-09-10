package com.higoods.domain.item.command

data class ItemOptionGroupCommand(
    val name: String,
    val itemOptions: List<ItemOptionCommand>
)
