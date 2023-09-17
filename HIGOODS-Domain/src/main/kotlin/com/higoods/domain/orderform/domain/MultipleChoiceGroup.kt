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

    val question: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "multipleChoiceGroupId")
    val choices: List<MultipleChoiceQuestion> = emptyList()

) : BaseEntity() {

    fun toOrderFormContent(): OrderFormContent {
        return OrderFormContent(
            type = OrderFormType.MULTIPLE_CHOICE,
            question = question,
            choices = choices.map {
                MultipleChoiceContent(
                    id = it.id,
                    optionDisplayName = it.optionDisplayName
                )
            }
        )
    }
}
