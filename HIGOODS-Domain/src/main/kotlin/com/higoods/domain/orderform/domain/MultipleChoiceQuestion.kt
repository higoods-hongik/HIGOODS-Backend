package com.higoods.domain.orderform.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_multiple_choice_question")
class MultipleChoiceQuestion(

    val multipleChoiceGroupId: Long,
    val question: String

) : BaseEntity()
