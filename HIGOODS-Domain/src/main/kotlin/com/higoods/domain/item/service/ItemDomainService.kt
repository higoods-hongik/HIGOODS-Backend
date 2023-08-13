package com.higoods.domain.item.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.item.adapter.ItemAdapter

@DomainService
class ItemDomainService(
    val itemAdapter: ItemAdapter
)
