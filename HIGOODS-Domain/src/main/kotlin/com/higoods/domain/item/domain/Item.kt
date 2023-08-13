package com.higoods.domain.item.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tbl_item")
class Item(

    val projectId: Long,
    val name: String,
    @Enumerated(EnumType.STRING)
    val category: ProductCategory,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    val itemOptionGroups: List<ItemOptionGroup> = emptyList()

) : BaseEntity()
