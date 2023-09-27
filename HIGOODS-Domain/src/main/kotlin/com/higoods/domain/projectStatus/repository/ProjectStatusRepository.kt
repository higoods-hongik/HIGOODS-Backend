package com.higoods.domain.projectStatus.repository

import com.higoods.domain.projectStatus.domain.ProjectStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectStatusRepository : JpaRepository<ProjectStatus, Long> {
    fun findAllByProjectId(projectId: Long): List<ProjectStatus>

    fun findTopByProjectId(projectId: Long): ProjectStatus?
}
