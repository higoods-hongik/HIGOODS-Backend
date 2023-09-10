package com.higoods.api.item.usecase

import com.higoods.api.item.dto.response.ItemResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.item.adapter.ItemAdapter
import org.springframework.transaction.annotation.Transactional

@UseCase
class ItemReadUseCase(
    private val itemAdapter: ItemAdapter
) {

    @Transactional
    fun findById(itemId: Long): ItemResponse {
        val item = itemAdapter.queryItem(itemId)
        return ItemResponse.of(item)
    }
}
