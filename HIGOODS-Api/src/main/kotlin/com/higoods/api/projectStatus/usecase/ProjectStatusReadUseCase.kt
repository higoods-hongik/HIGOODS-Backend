package com.higoods.api.projectStatus.usecase

import com.higoods.api.projectStatus.dto.response.ProjectStatusResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.projectStatus.adapter.ProjectStatusAdapter

@UseCase
class ProjectStatusReadUseCase(
    private val projectAdapter: ProjectAdapter,
    private val projectStatusAdapter: ProjectStatusAdapter
) {
    fun findAllByProjectId(projectId: Long): List<ProjectStatusResponse> {
        val project = projectAdapter.queryById(projectId)
        val findAll = projectStatusAdapter.findAllByProjectId(project.id)
        return findAll.map {
                projectStatus ->
            ProjectStatusResponse.of(projectStatus)
        }
    }
}
