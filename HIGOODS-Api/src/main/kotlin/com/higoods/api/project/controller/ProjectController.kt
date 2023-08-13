package com.higoods.api.project.controller

import com.higoods.api.project.dto.request.ProjectCreateRequest
import com.higoods.api.project.dto.response.ProjectResponse
import com.higoods.api.project.usecase.ProjectCreateUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// @Tag(name = "1-1. [인증]")
@RestController
@RequestMapping("/v1/projects")
class ProjectController(
    val projectCreateUseCase: ProjectCreateUseCase
) {

    @PostMapping
    fun kakaoAuthCheckRegisterValid(
        @Validated @RequestBody
        projectCreateRequest: ProjectCreateRequest
    ): ProjectResponse {
        return projectCreateUseCase.execute(projectCreateRequest)
    }
}
