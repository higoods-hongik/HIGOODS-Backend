package com.higoods.api.project.usecase

import com.higoods.api.project.dto.response.ProjectResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.user.adapter.UserAdapter
import com.higoods.domain.user.exception.UserNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@UseCase
class ProjectReadUseCase(
    private val projectAdapter: ProjectAdapter,
    private val userAdapter: UserAdapter
) {

    fun findById(projectId: Long): ProjectResponse {
        val project = projectAdapter.queryById(projectId)
        val user = userAdapter.queryUser(project.userId)
        return ProjectResponse.of(project, user.toUserInfoVo())
    }

    fun findAll(pageable: Pageable): Page<ProjectResponse> {
        val findAll = projectAdapter.findAll(pageable)
        val userIds = findAll.map { project ->
            project.userId
        }.toList()
        val users = userAdapter.queryUsers(userIds)
        return findAll.map { project ->
            val user = users.find { it.id == project.userId } ?: throw UserNotFoundException.EXCEPTION
            ProjectResponse.of(project, user.toUserInfoVo())
        }
    }
}
