package com.higoods.api.projectStatus.dto.request

import com.higoods.domain.projectStatus.domain.ProjectStatus

data class ProjectStatusCreateRequest(
    val keyword: String,
    val description: String
) {
    fun toProjectStatus(projectId: Long): ProjectStatus {
        return ProjectStatus(
            projectId = projectId,
            keyword = this.keyword,
            description = this.description
        )
    }
}
