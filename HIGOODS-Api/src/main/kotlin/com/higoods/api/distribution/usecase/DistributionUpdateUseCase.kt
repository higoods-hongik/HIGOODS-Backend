package com.higoods.api.distribution.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.distribution.dto.response.DistributionStateResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.distribution.adapter.DistributionAdapter
import com.higoods.domain.distribution.service.DistributionDomainService
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.exception.ProjectNotHostException

@UseCase
class DistributionUpdateUseCase(
    private val distributionAdapter: DistributionAdapter,
    private val projectAdapter: ProjectAdapter,
    private val distributionDomainService: DistributionDomainService
) {
    fun updateState(distributionId: Long, isReceived: Boolean): DistributionStateResponse {
        val distribution = distributionAdapter.queryById(distributionId)
        val project = projectAdapter.queryById(distribution.projectId)
        if (project.userId != SecurityUtils.currentUserId) {
            throw ProjectNotHostException.EXCEPTION
        }
        distributionDomainService.updateState(distribution, isReceived)
        return DistributionStateResponse(distribution.id)
    }
}
