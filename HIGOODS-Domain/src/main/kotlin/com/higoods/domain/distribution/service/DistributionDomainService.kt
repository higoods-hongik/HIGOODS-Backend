package com.higoods.domain.distribution.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.distribution.adapter.DistributionAdapter
import com.higoods.domain.distribution.domain.Distribution

@DomainService
class DistributionDomainService(
    private val distributionAdapter: DistributionAdapter
) {
    fun createDistribution(projectId: Long, orderId: Long, userId: Long): Distribution {
        return distributionAdapter.saveDistribution(Distribution(projectId, orderId, userId, null))
    }
}
