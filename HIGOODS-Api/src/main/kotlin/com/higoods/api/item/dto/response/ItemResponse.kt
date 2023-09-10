package com.higoods.api.item.dto.response

import com.higoods.domain.item.domain.Item
import com.higoods.domain.item.domain.ProductCategory

data class ItemResponse(
    val projectId: Long,
    val category: ProductCategory,
    val name: String,
    val itemOptionGroups: List<ItemOptionGroupResponse>,
    val id: Long
) {
    companion object {
        fun of(newItem: Item): ItemResponse {
            return ItemResponse(
                projectId = newItem.projectId,
                category = newItem.category,
                name = newItem.name,
                id = newItem.id,
                itemOptionGroups = newItem.itemOptionGroups.map { itemOptionGroup ->
                    ItemOptionGroupResponse(
                        name = itemOptionGroup.name,
                        id = itemOptionGroup.id,
                        itemOptions = itemOptionGroup.itemOptions.map { itemOption ->
                            ItemOptionResponse(
                                name = itemOption.name,
                                price = itemOption.price,
                                id = itemOption.id
                            )
                        }
                    )
                }
            )
        }
    }
}
