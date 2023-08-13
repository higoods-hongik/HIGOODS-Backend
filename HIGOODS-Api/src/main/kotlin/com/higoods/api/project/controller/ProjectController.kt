package com.higoods.api.project.controller

import com.higoods.api.project.dto.request.ProjectCreateRequest
import com.higoods.api.project.dto.response.ProjectResponse
import com.higoods.api.project.usecase.ProjectCreateUseCase
import com.higoods.api.project.usecase.ProjectReadUseCase
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// @Tag(name = "1-1. [인증]")
@RestController
@RequestMapping("/v1/projects")
class ProjectController(
    val projectCreateUseCase: ProjectCreateUseCase,
    val projectReadUseCase: ProjectReadUseCase
) {

    @PostMapping
    fun create(
        @Validated @RequestBody
        projectCreateRequest: ProjectCreateRequest
    ): ProjectResponse {
        return projectCreateUseCase.execute(projectCreateRequest)
    }

    @GetMapping("/{project_id}")
    fun getProjectById(
        @PathVariable("project_id") projectId: Long
    ): ProjectResponse {
        return projectReadUseCase.findById(projectId)
    }

    @GetMapping
    fun findAll(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Page<ProjectResponse> {
        return projectReadUseCase.findAll(PageRequest.of(page, size))
    }
}
