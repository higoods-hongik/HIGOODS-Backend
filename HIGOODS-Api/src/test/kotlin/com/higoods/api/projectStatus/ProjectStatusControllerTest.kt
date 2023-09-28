package com.higoods.api.projectStatus

import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.DocumentObjects
import com.higoods.api.common.NUMBER
import com.higoods.api.common.OpenApiTag
import com.higoods.api.common.STRING
import com.higoods.api.projectStatus.controller.ProjectStatusController
import com.higoods.api.projectStatus.dto.response.ProjectStatusResponse
import com.higoods.api.projectStatus.usecase.ProjectStatusReadUseCase
import io.mockk.every
import io.mockk.mockk

class ProjectStatusControllerTest : BaseControllerTest() {
    private val projectStatusReadUseCase: ProjectStatusReadUseCase = mockk()

    override val controller: ProjectStatusController = ProjectStatusController(
        projectStatusReadUseCase = projectStatusReadUseCase
    )

    private fun projectStatusResponse(): ProjectStatusResponse {
        return ProjectStatusResponse(
            projectStatusId = 1L,
            projectId = 1L,
            keyword = "구매 신청",
            description = "프로젝트가 오픈되어 주문을 받고 있습니다.",
            createdAt = DocumentObjects.dateTimeFormatString
        )
    }

    init {
        test("프로젝트 현황 타임라인 조회 ") {

            every { projectStatusReadUseCase.findAllByProjectId(any()) } returns listOf(projectStatusResponse())

            get("/v1/projects/status/{project_id}", pathParams = arrayOf("1")) {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "프로젝트 현황 타임라인 조회",
                        tag = OpenApiTag.PROJECT_STATUS,
                        description = "프로젝트 현황 타임라인 조회 API"
                    ),
                    pathParameters("project_id" type STRING means "프로젝트 아이디"),
                    responseFields(
                        "[].projectStatusId" type NUMBER means "프로젝트 현황 id",
                        "[].projectId" type NUMBER means "프로젝트 id",
                        "[].keyword" type STRING means "키워드",
                        "[].description" type STRING means "설명",
                        "[].createdAt" type STRING means "업데이트 날짜"
                    )
                )
        }
    }
}
