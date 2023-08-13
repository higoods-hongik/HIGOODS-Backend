package com.higoods.domain.projectStatus.domain

import com.higoods.domain.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_project_status")
class ProjectStatus(

    val projectId: Long, // 프로젝트 아이디

    var keyword: String, // 키워드
    var description: String // 설명

) : BaseEntity()
