package com.higoods.api.projectStatus.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.projectStatus.dto.request.ProjectStatusCreateRequest
import com.higoods.api.projectStatus.dto.response.ProjectStatusResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.exception.ProjectNotHostException
import com.higoods.domain.projectStatus.adapter.ProjectStatusAdapter
import com.higoods.domain.projectStatus.service.ProjectStatusDomainService

@UseCase
class ProjectStatusCreateUseCase(
    private val projectStatusDomainService: ProjectStatusDomainService,
    private val projectStatusAdapter: ProjectStatusAdapter,
    private val projectAdapter: ProjectAdapter
) {
    fun execute(projectId: Long, projectStatusCreateRequest: ProjectStatusCreateRequest): ProjectStatusResponse {
        // TODO: 이메일 전송 기능 추가 후 이메일 전송 로직 추가 필요
        val project = projectAdapter.queryById(projectId)
        if (project.userId != SecurityUtils.currentUserId) throw ProjectNotHostException.EXCEPTION
        val newProjectStatusId = projectStatusDomainService.create(projectStatusCreateRequest.toProjectStatus(projectId))
        val projectStatus = projectStatusAdapter.queryById(newProjectStatusId)
        return ProjectStatusResponse.of(projectStatus)
    }
}
