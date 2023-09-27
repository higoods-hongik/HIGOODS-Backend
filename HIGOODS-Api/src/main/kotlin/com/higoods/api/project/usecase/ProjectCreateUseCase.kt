package com.higoods.api.project.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.project.dto.request.ProjectCreateRequest
import com.higoods.api.project.dto.response.ProjectResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.service.ProjectDomainService
import com.higoods.domain.projectStatus.service.ProjectStatusDomainService
import com.higoods.domain.user.adapter.UserAdapter

@UseCase
class ProjectCreateUseCase(
    private val projectDomainService: ProjectDomainService,
    private val projectAdapter: ProjectAdapter,
    private val userAdapter: UserAdapter,
    private val projectStatusDomainService: ProjectStatusDomainService
) {

    fun execute(projectCreateRequest: ProjectCreateRequest): ProjectResponse {
        val newProjectId = projectDomainService.create(projectCreateRequest.toProject(SecurityUtils.currentUserId))
        val project = projectAdapter.queryById(newProjectId)
        val user = userAdapter.queryUser(project.userId)
        projectStatusDomainService.createInit(project)
        return ProjectResponse.of(project, user.toUserInfoVo())
    }
}
