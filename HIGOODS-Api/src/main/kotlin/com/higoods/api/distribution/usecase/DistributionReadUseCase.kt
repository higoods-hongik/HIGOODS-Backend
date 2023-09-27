package com.higoods.api.distribution.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.distribution.dto.response.DistributionResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.distribution.adapter.DistributionAdapter
import com.higoods.domain.order.exception.OrderNotFoundException
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.exception.ProjectNotHostException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@UseCase
class DistributionReadUseCase(
    private val distributionAdapter: DistributionAdapter,
    private val orderAdapter: com.higoods.domain.order.adapter.OrderAdapter,
    private val projectAdapter: ProjectAdapter
) {
    fun findAll(projectId: Long, name: String?, pageable: Pageable): Page<DistributionResponse> {
        val project = projectAdapter.queryById(projectId)
        if (project.userId != SecurityUtils.currentUserId) {
            throw ProjectNotHostException.EXCEPTION
        }

        val findAll = distributionAdapter.findAll(projectId, name, pageable)
        val orderIds = findAll.map { distribution -> distribution.orderId }.toList()
        val orders = orderAdapter.findAllById(orderIds)

        return findAll.map { distribution ->
            val order = orders.find { it.id == distribution.orderId } ?: throw OrderNotFoundException.EXCEPTION
            DistributionResponse.of(order = order, distribution = distribution)
        }
    }

    fun findAllExcel(projectId: Long): List<DistributionResponse> {
        val project = projectAdapter.queryById(projectId)
        if (project.userId != SecurityUtils.currentUserId) {
            throw ProjectNotHostException.EXCEPTION
        }

        val findAll = distributionAdapter.findAll(projectId)
        val orderIds = findAll.map { distribution -> distribution.orderId }.toList()
        val orders = orderAdapter.findAllById(orderIds)

        return findAll.map { distribution ->
            val order = orders.find { it.id == distribution.orderId } ?: throw OrderNotFoundException.EXCEPTION
            DistributionResponse.of(order = order, distribution = distribution)
        }
    }
}
