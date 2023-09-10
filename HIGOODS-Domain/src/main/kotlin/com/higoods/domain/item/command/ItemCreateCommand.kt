package com.higoods.domain.item.command

import com.higoods.domain.item.domain.Item
import com.higoods.domain.item.domain.ItemOption
import com.higoods.domain.item.domain.ItemOptionGroup
import com.higoods.domain.item.domain.ProductCategory

data class ItemCreateCommand(
    val projectId: Long,
    val category: ProductCategory,
    val name: String,
    val itemOptionGroups: List<ItemOptionGroupCommand>
) {
    fun toDomain(): Item {
        return Item(
            projectId = projectId,
            category = category,
            name = name,
            itemOptionGroups = itemOptionGroups.map { itemOptionGroup ->
                ItemOptionGroup(
                    name = itemOptionGroup.name,
                    itemOptions = itemOptionGroup.itemOptions.map { itemOption ->
                        ItemOption(
                            name = itemOption.name,
                            price = itemOption.price
                        )
                    }
                )
            }
        )
    }
}
