package com.higoods.domain.projectStatus.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.project.domain.Project
import com.higoods.domain.projectStatus.adapter.ProjectStatusAdapter
import com.higoods.domain.projectStatus.domain.ProjectStatus
import org.springframework.transaction.annotation.Transactional

@DomainService
class ProjectStatusDomainService(
    private val projectStatusAdapter: ProjectStatusAdapter
) {
    @Transactional
    fun createInit(project: Project) {
        projectStatusAdapter.save(project.id)
    }

    @Transactional
    fun create(projectStatus: ProjectStatus): Long {
        return projectStatusAdapter.save(projectStatus).id
    }
}
