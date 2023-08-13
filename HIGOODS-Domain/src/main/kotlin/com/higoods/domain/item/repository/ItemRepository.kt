package com.higoods.domain.item.repository

import com.higoods.domain.item.domain.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long>
