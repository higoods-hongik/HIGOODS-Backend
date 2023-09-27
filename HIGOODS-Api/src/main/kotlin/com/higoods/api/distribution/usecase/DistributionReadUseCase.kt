package com.higoods.api.distribution.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.distribution.dto.response.DistributionResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.distribution.adapter.DistributionAdapter
import com.higoods.domain.order.exception.OrderNotFoundException
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.exception.ProjectNotHostException
import com.higoods.domain.user.adapter.UserAdapter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.jaxb.OrderAdapter

@UseCase
class DistributionReadUseCase(
    private val distributionAdapter: DistributionAdapter,
    private val userAdapter: UserAdapter,
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

//        val filteredList = findAll
//            .map { distribution ->
//                val order = orders.find { it.id == distribution.orderId } ?: throw OrderNotFoundException.EXCEPTION
//                DistributionResponse.of(order = order, distribution = distribution)
//            }
//            .filter { distributionResponse ->
//                name == null || distributionResponse.name == name
//            }
//
//        val start = pageable.offset.toInt()
//        val end = minOf((start + pageable.pageSize), filteredList.size())
//        return PageImpl(filteredList.subList(start, end), pageable, filteredList.siz.toLong())

//        return findAll
//            .map { distribution ->
//                val order = orders.find { it.id == distribution.orderId } ?: throw OrderNotFoundException.EXCEPTION
//                DistributionResponse.of(order = order, distribution = distribution)
//            }
//            .filter { distributionResponse ->
//                name == null || distributionResponse.order.name == name
//            }.toPageable(pageable)
//
        return findAll.map { distribution ->
            val order = orders.find { it.id == distribution.orderId } ?: throw OrderNotFoundException.EXCEPTION
            DistributionResponse.of(order = order, distribution = distribution)
        }
    }
}
