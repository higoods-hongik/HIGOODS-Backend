package com.higoods.api.projectStatus.controller

import com.higoods.api.projectStatus.dto.response.ProjectStatusResponse
import com.higoods.api.projectStatus.usecase.ProjectStatusReadUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/projects/status")
class ProjectStatusController(
    val projectStatusReadUseCase: ProjectStatusReadUseCase
) {
    // 프로젝트 현황 타임라인 조회 API
    @GetMapping("/{project_id}")
    fun findAllByProjectId(
        @PathVariable("project_id") projectId: Long
    ): List<ProjectStatusResponse> {
        return projectStatusReadUseCase.findAllByProjectId(projectId)
    }
}
