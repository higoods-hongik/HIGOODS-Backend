package com.higoods.domain.item.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.item.domain.Item
import com.higoods.domain.item.exception.ItemNotFoundException
import com.higoods.domain.item.repository.ItemRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class ItemAdapter(
    private val itemRepository: ItemRepository
) {

    fun queryItem(itemId: Long): Item {
        return itemRepository.findByIdOrNull(itemId) ?: run { throw ItemNotFoundException.EXCEPTION }
    }

    fun save(item: Item): Item {
        return itemRepository.save(item)
    }
}
