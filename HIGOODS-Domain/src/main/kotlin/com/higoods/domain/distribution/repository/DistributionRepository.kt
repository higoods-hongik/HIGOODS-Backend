package com.higoods.domain.distribution.repository

import com.higoods.domain.distribution.domain.Distribution
import org.springframework.data.jpa.repository.JpaRepository

interface DistributionRepository : JpaRepository<Distribution, Long> {
    fun deleteByOrderId(orderId: Long)

    fun findAllByProjectId(projectId: Long): List<Distribution>
}
