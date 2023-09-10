package com.higoods.domain.item.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tbl_item_option_group")
class ItemOptionGroup(

    val name: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "optionGroupId")
    val itemOptions: List<ItemOption> = emptyList()

) : BaseEntity()
