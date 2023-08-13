package com.higoods.api.project.usecase

import com.higoods.api.project.dto.request.ProjectUpdateRequest
import com.higoods.api.project.dto.response.ProjectResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.service.ProjectDomainService
import com.higoods.domain.user.adapter.UserAdapter

@UseCase
class ProjectUpdateUseCase(
    private val projectDomainService: ProjectDomainService,
    private val projectAdapter: ProjectAdapter,
    private val userAdapter: UserAdapter
) {

    fun execute(projectId: Long, projectUpdateRequest: ProjectUpdateRequest): ProjectResponse {
        val newProjectId = projectDomainService.update(
            projectId,
            projectUpdateRequest.content,
            projectUpdateRequest.subTitle,
            projectUpdateRequest.titleImage
        )
        val project = projectAdapter.queryById(newProjectId)
        val user = userAdapter.queryUser(project.userId)
        return ProjectResponse.of(project, user.toUserInfoVo())
    }
}
