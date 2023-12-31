package com.higoods.domain.orderform.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

/**
 * 주관식 설문
 */
@Entity
@Table(name = "tbl_short_form_question")
class ShortFormQuestion(

    val orderFormId: Long,
    val question: String

) : BaseEntity() {
    fun toOrderFormContent(): OrderFormContent {
        return OrderFormContent(
            type = OrderFormType.SHORT_FORM,
            question = question
        )
    }
}
