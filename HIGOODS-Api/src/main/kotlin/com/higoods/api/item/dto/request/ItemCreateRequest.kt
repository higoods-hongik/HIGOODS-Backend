package com.higoods.api.item.dto.request

import com.higoods.domain.item.command.ItemCreateCommand
import com.higoods.domain.item.command.ItemOptionCommand
import com.higoods.domain.item.command.ItemOptionGroupCommand
import com.higoods.domain.item.domain.ProductCategory

data class ItemCreateRequest(
    val category: ProductCategory,
    val name: String,
    val itemOptionGroups: List<ItemOptionGroupRequest>
) {
    fun toCommand(projectId: Long): ItemCreateCommand {
        return ItemCreateCommand(
            projectId = projectId,
            category = category,
            name = name,
            itemOptionGroups = itemOptionGroups.map { itemOptionGroup ->
                ItemOptionGroupCommand(
                    name = itemOptionGroup.name,
                    itemOptions = itemOptionGroup.itemOptions.map { itemOption ->
                        ItemOptionCommand(
                            name = itemOption.name,
                            price = itemOption.price
                        )
                    }
                )
            }
        )
    }
}
