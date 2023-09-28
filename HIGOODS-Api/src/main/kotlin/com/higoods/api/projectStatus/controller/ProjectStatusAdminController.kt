package com.higoods.api.projectStatus.controller

import com.higoods.api.projectStatus.dto.request.ProjectStatusCreateRequest
import com.higoods.api.projectStatus.dto.response.ProjectStatusResponse
import com.higoods.api.projectStatus.usecase.ProjectStatusCreateUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admins/projects/status")
class ProjectStatusAdminController(
    val projectStatusCreateUseCase: ProjectStatusCreateUseCase
) {
    // [어드민] 프로젝트 현황 키워드 업데이트 API
    @PostMapping("/{project_id}")
    fun create(
        @PathVariable("project_id") projectId: Long,
        @Validated @RequestBody
        projectStatusCreateRequest: ProjectStatusCreateRequest
    ): ProjectStatusResponse {
        return projectStatusCreateUseCase.execute(projectId, projectStatusCreateRequest)
    }
}
