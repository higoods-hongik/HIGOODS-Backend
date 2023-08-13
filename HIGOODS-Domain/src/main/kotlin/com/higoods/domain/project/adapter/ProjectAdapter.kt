package com.higoods.domain.project.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.project.domain.Project
import com.higoods.domain.project.repository.ProjectRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class ProjectAdapter(
    private val projectRepository: ProjectRepository
) {

    fun save(project: Project): Project {
        return projectRepository.save(project)
    }

    fun queryById(projectId: Long): Project {
        return projectRepository.findByIdOrNull(projectId) ?: throw ProjectNotFoundException()
    }
}
