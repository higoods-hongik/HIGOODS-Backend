package com.higoods.api.projectStatus

import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.DocumentObjects
import com.higoods.api.common.NUMBER
import com.higoods.api.common.OpenApiTag
import com.higoods.api.common.STRING
import com.higoods.api.projectStatus.controller.ProjectStatusAdminController
import com.higoods.api.projectStatus.dto.request.ProjectStatusCreateRequest
import com.higoods.api.projectStatus.dto.response.ProjectStatusResponse
import com.higoods.api.projectStatus.usecase.ProjectStatusCreateUseCase
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType

class ProjectStatusAdminControllerTest : BaseControllerTest() {
    private val projectStatusCreateUseCase: ProjectStatusCreateUseCase = mockk()

    override val controller: ProjectStatusAdminController = ProjectStatusAdminController(
        projectStatusCreateUseCase = projectStatusCreateUseCase
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
        test("[어드민] 프로젝트 현황 키워드 업데이트") {

            val projectStatusCreateRequest = ProjectStatusCreateRequest(
                keyword = "구매 신청",
                description = "프로젝트가 오픈되어 주문을 받고 있습니다."
            )

            every { projectStatusCreateUseCase.execute(any(), any()) } returns projectStatusResponse()

            post("/v1/admins/projects/status/{project_id}", pathParams = arrayOf("1")) {
                contentType(MediaType.APPLICATION_JSON)
                accept(MediaType.APPLICATION_JSON)
                content(objectMapper.writeValueAsString(projectStatusCreateRequest))
                authorizationHeader(1)
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "[어드민] 프로젝트 현황 키워드 업데이트",
                        tag = OpenApiTag.ADMIN_PROJECT_STATUS,
                        description = "[어드민] 프로젝트 현황 키워드 업데이트 API, 프로젝트 현황 타임라인 값을 추가합니다."
                    ),
                    pathParameters("project_id" type STRING means "프로젝트 아이디"),
                    requestFields(
                        "keyword" type STRING means "키워드",
                        "description" type STRING means "설명"
                    ),
                    responseFields(
                        "projectStatusId" type NUMBER means "프로젝트 현황 id",
                        "projectId" type NUMBER means "프로젝트 id",
                        "keyword" type STRING means "키워드",
                        "description" type STRING means "설명",
                        "createdAt" type STRING means "현황 업데이트 날짜"
                    )
                )
        }
    }
}
