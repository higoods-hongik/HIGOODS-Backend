package com.higoods.domain.projectStatus.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.projectStatus.domain.ProjectStatus
import com.higoods.domain.projectStatus.exception.ProjectStatusNotFoundException
import com.higoods.domain.projectStatus.repository.ProjectStatusRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class ProjectStatusAdapter(
    private val projectStatusRepository: ProjectStatusRepository
) {
    fun save(projectStatus: ProjectStatus): ProjectStatus {
        return projectStatusRepository.save(projectStatus)
    }

    fun save(projectId: Long): ProjectStatus {
        val projectStatus = ProjectStatus(projectId = projectId, keyword = "구매 신청", description = "프로젝트가 오픈되어 주문을 받고 있습니다.")
        return projectStatusRepository.save(projectStatus)
    }

    fun findAllByProjectId(projectId: Long): List<ProjectStatus> {
        return projectStatusRepository.findAllByProjectId(projectId)
    }

    fun queryById(projectStatusId: Long): ProjectStatus {
        return projectStatusRepository.findByIdOrNull(projectStatusId) ?: throw ProjectStatusNotFoundException.EXCEPTION
    }
}
