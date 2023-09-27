package com.higoods.domain.distribution.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.common.lock.RedissonLock
import com.higoods.domain.distribution.adapter.DistributionAdapter
import com.higoods.domain.distribution.domain.Distribution

@DomainService
class DistributionDomainService(
    private val distributionAdapter: DistributionAdapter
) {
    fun createDistribution(projectId: Long, orderId: Long, userId: Long): Distribution {
        return distributionAdapter.saveDistribution(Distribution(projectId, orderId, userId, null))
    }

    @RedissonLock(key = "#distribution.id", lockName = "배부 상태 변경")
    fun updateState(distribution: Distribution, isReceived: Boolean) {
        if (isReceived) {
            return distribution.received()
        }
        return distribution.notReceived()
    }
}
