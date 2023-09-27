package com.higoods.api.distribution.controller

import com.higoods.api.distribution.dto.response.DistributionResponse
import com.higoods.api.distribution.dto.response.DistributionStateResponse
import com.higoods.api.distribution.usecase.DistributionReadUseCase
import com.higoods.api.distribution.usecase.DistributionUpdateUseCase
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admins/distributions")
class DistributionAdminController(
    val distributionReadUseCase: DistributionReadUseCase,
    val distributionUpdateUseCase: DistributionUpdateUseCase
) {
    // [어드민] 현장 배부 목록 조회
    @GetMapping("/{project_id}")
    fun findAll(
        @PathVariable("project_id") projectId: Long,
        @RequestParam("name", required = false) name: String?,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Page<DistributionResponse> {
        return distributionReadUseCase.findAll(projectId, name, PageRequest.of(page, size))
    }

    // [어드민] 배부 상태 변경
    @PatchMapping("/{distribution_id}")
    fun updateState(
        @PathVariable("distribution_id") distributionId: Long,
        @RequestParam("isReceived") isReceived: Boolean
    ): DistributionStateResponse {
        return distributionUpdateUseCase.updateState(distributionId, isReceived)
    }
}
