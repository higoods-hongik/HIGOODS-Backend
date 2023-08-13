package com.higoods.api.project.usecase

import com.higoods.api.project.dto.request.ProjectCreateRequest
import com.higoods.api.project.dto.response.ProjectResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.service.ProjectDomainService
import com.higoods.domain.user.adapter.UserAdapter

@UseCase
class ProjectCreateUseCase(
    private val projectDomainService: ProjectDomainService,
    private val projectAdapter: ProjectAdapter,
    private val userAdapter: UserAdapter
) {

    fun execute(projectCreateRequest: ProjectCreateRequest): ProjectResponse {
        val newProjectId = projectDomainService.create(projectCreateRequest.toProject())
        val project = projectAdapter.queryById(newProjectId)
        val user = userAdapter.queryUser(project.userId)
        return ProjectResponse.of(project, user.toUserInfoVo())
    }
}
