package com.higoods.domain.item.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_item_option_group")
class ItemOptionGroup(

    val itemId: Long,
    val name: String

) : BaseEntity()
