package com.higoods.domain.distribution.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.distribution.domain.Distribution
import com.higoods.domain.distribution.exception.DistributionNotFoundException
import com.higoods.domain.distribution.repository.DistributionQuerydslRepository
import com.higoods.domain.distribution.repository.DistributionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull

@Adapter
class DistributionAdapter(
    private val distributionRepository: DistributionRepository,
    private val distributionQuerydslRepository: DistributionQuerydslRepository
) {
    fun saveDistribution(distribution: Distribution): Distribution {
        return distributionRepository.save(distribution)
    }

    fun findAll(projectId: Long, name: String?, pageable: Pageable): Page<Distribution> {
        return distributionQuerydslRepository.findAllByProjectIdAndName(projectId, name, pageable)
    }

    fun deleteByOrderId(orderId: Long) {
        return distributionRepository.deleteByOrderId(orderId)
    }

    fun queryById(distributionId: Long): Distribution {
        return distributionRepository.findByIdOrNull(distributionId) ?: throw DistributionNotFoundException.EXCEPTION
    }
}
