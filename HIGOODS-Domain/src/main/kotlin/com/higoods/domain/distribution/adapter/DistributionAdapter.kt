package com.higoods.domain.distribution.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.distribution.domain.Distribution
import com.higoods.domain.distribution.repository.DistributionRepository

@Adapter
class DistributionAdapter(
    private val distributionRepository: DistributionRepository
) {
    fun saveDistribution(distribution: Distribution): Distribution {
        return distributionRepository.save(distribution)
    }
}
