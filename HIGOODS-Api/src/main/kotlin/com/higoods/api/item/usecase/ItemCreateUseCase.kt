package com.higoods.api.item.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.api.item.dto.request.ItemCreateRequest
import com.higoods.api.item.dto.response.ItemResponse
import com.higoods.common.annotation.UseCase
import com.higoods.domain.item.service.ItemDomainService
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.domain.ProjectProgress
import com.higoods.domain.project.exception.ProjectNotHostException
import com.higoods.domain.project.exception.ProjectNotItemCreateStatusException
import org.springframework.transaction.annotation.Transactional

@UseCase
class ItemCreateUseCase(
    private val itemDomainService: ItemDomainService,
    private val projectAdapter: ProjectAdapter
) {

    @Transactional
    fun execute(projectId: Long, itemCreateRequest: ItemCreateRequest): ItemResponse {
        val project = projectAdapter.queryById(projectId)

        if (project.userId != SecurityUtils.currentUserId) {
            throw ProjectNotHostException.EXCEPTION
        }

        if (project.progress != ProjectProgress.PREPARING) {
            throw ProjectNotItemCreateStatusException.EXCEPTION
        }

        val newItem = itemDomainService.create(itemCreateRequest.toCommand(projectId))

        return ItemResponse.of(newItem)
    }
}
