package com.higoods.domain.item.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.item.adapter.ItemAdapter
import com.higoods.domain.item.command.ItemCreateCommand
import com.higoods.domain.item.domain.Item

@DomainService
class ItemDomainService(
    val itemAdapter: ItemAdapter
) {
    fun create(command: ItemCreateCommand): Item {
        return itemAdapter.save(
            command.toDomain()
        )
    }
}
