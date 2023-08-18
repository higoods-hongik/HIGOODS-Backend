package com.higoods.api.order.dto.response

import com.higoods.domain.projectStatus.domain.ProjectStatus

data class OrderProjectsResponse(
    val orderId: Long,
    val title: String,
    val titleImage: String,
    val subTitle: String,
    // TODO: 프로젝트단 관련 작업 완료되면 추가 작업 필요
    // val category: String,
    val projectStatus: ProjectStatus
)
