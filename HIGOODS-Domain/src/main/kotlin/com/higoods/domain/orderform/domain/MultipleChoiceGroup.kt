package com.higoods.domain.orderform.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * 단답식 설문 그룹
 */
@Entity
@Table(name = "tbl_multiple_choice_group")
class MultipleChoiceGroup(

    val orderFormId: Long,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "multipleChoiceGroupId")
    val questions: List<MultipleChoiceQuestion> = emptyList()

) : BaseEntity()
