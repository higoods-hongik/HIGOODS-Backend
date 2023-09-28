package com.higoods.api.projectStatus.dto.response

import com.higoods.common.helper.toHigoodsDateTimeFormat
import com.higoods.domain.projectStatus.domain.ProjectStatus

data class ProjectStatusResponse(
    val projectStatusId: Long,
    val projectId: Long,
    val keyword: String,
    val description: String,
    val createdAt: String
) {
    companion object {
        fun of(projectStatus: ProjectStatus): ProjectStatusResponse {
            return ProjectStatusResponse(
                projectStatusId = projectStatus.id,
                projectId = projectStatus.projectId,
                keyword = projectStatus.keyword,
                description = projectStatus.description,
                createdAt = projectStatus.createdAt.toHigoodsDateTimeFormat()
            )
        }
    }
}
