package com.higoods.domain.project.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.domain.Project
import org.springframework.transaction.annotation.Transactional

@DomainService
class ProjectDomainService(
    private val projectAdapter: ProjectAdapter
) {

    @Transactional
    fun create(project: Project): Long {
        return projectAdapter.save(project).id
    }
}
