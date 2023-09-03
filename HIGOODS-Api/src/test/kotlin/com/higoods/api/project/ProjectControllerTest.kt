package com.higoods.api.project

import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.DocumentObjects
import com.higoods.api.common.DocumentObjects.getPageResponse
import com.higoods.api.common.DocumentObjects.type
import com.higoods.api.common.ENUM
import com.higoods.api.common.NUMBER
import com.higoods.api.common.OpenApiTag
import com.higoods.api.common.STRING
import com.higoods.api.project.controller.ProjectController
import com.higoods.api.project.dto.request.ProjectCreateRequest
import com.higoods.api.project.dto.request.ProjectUpdateRequest
import com.higoods.api.project.dto.response.ProjectResponse
import com.higoods.api.project.usecase.ProjectCreateUseCase
import com.higoods.api.project.usecase.ProjectReadUseCase
import com.higoods.api.project.usecase.ProjectUpdateUseCase
import com.higoods.domain.common.vo.UserInfoVo
import com.higoods.domain.project.domain.ShipmentType
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.domain.PageImpl

class ProjectControllerTest : BaseControllerTest() {
    private val projectCreateUseCase: ProjectCreateUseCase = mockk()
    private val projectReadUseCase: ProjectReadUseCase = mockk()
    private val projectUpdateUseCase: ProjectUpdateUseCase = mockk()

    override val controller: ProjectController = ProjectController(
        projectCreateUseCase = projectCreateUseCase,
        projectReadUseCase = projectReadUseCase,
        projectUpdateUseCase = projectUpdateUseCase
    )

    init {
        test("POST /v1/projects") {

            every { projectCreateUseCase.execute(any()) } returns projectResponse()

            val request = ProjectCreateRequest(
                title = "제목",
                titleImage = "대표이미지",
                subTitle = "subTitle",
                minimumPurchaseQuantity = 10L,
                endDateTime = DocumentObjects.dateTimeFormatString,
                shipmentType = ShipmentType.BOTH
            )

            post("/v1/projects", request) {
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "프로젝트 생성",
                        tag = OpenApiTag.PROJECT,
                        description = "프로젝트를 첫번째 화면에 생성합니다."
                    ),
                    requestFields(
                        "title" type STRING means "프로젝트 제목",
                        "titleImage" type STRING means "프로젝트 대표이미지",
                        "subTitle" type STRING means "부제목",
                        "minimumPurchaseQuantity" type NUMBER means "최소 구매 가능 수량",
                        "endDateTime" type STRING means "끝나는 시간 포맷으로 보내주세용 yyyy-MM-ddTHH:mm ex 1999-11-27T11:22",
                        "shipmentType" type ENUM(ShipmentType::class) means "배송 관련 이넘 BOTH , DELIVERY ,DISTRIBUTION "
                    ),
                    responseFields(*projectResponseDocs())
                )
        }

        test("PATCH /v1/projects/{project_id}") {

            val projectUpdateRequest = ProjectUpdateRequest(
                titleImage = "타이틀 이미지",
                subTitle = "부제목",
                content = "콘텐츠"
            )

            every { projectUpdateUseCase.execute(any(), any()) } returns projectResponse()

            get("/v1/projects/{project_id}", pathParams = arrayOf("1")) {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "프로젝트 수정",
                        tag = OpenApiTag.PROJECT,
                        description = "프로젝트를 수정합니다. 중간단계때 호출 가능"
                    ),
                    pathParameters("project_id" type STRING means "프로젝트 아이디"),
                    responseFields(*projectResponseDocs())
                )
        }

        test("GET /v1/projects/{project_id}") {

            every { projectReadUseCase.findById(any()) } returns projectResponse()

            get("/v1/projects/{project_id}", pathParams = arrayOf("1")) {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "프로젝트 조회",
                        tag = OpenApiTag.PROJECT,
                        description = "프로젝트를 조회합니다"
                    ),
                    pathParameters("project_id" type STRING means "프로젝트 아이디"),
                    responseFields(*projectResponseDocs())
                )
        }

        test("GET /v1/projects") {

            every { projectReadUseCase.findAll(any()) } returns PageImpl(listOf(projectResponse()))

            get("/v1/projects") {
                queryParam("page", "10")
                queryParam("size", "10")
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "프로젝트 조회",
                        tag = OpenApiTag.PROJECT,
                        description = "프로젝트를 조회합니다"
                    ),
                    queryParameters(
                        "page" type STRING means "페이지",
                        "size" type STRING means "사이즈"
                    ),
                    getPageResponse(projectResponseDocs())
                )
        }
    }

    private fun projectResponseDocs() = arrayOf(
        "title" type STRING means "프로젝트 제목",
        "titleImage" type STRING means "프로젝트 대표이미지",
        "subTitle" type STRING means "부제목",
        "content" type STRING means "콘텐츠",
        "minimumPurchaseQuantity" type NUMBER means "프로젝트 제목",
        "endDateTime" type STRING means "끝나는 시간",
        "shipmentType" type ENUM(ShipmentType::class) means "배송 관련 이넘 BOTH , DELIVERY ,DISTRIBUTION",
        *DocumentObjects.userInfoVo,
        "createAt" type STRING means "생성 시간"
    )

    private fun projectResponse(): ProjectResponse {
        val response = ProjectResponse(
            title = "제목",
            titleImage = "대표이미지",
            subTitle = "subTitle",
            content = "content",
            minimumPurchaseQuantity = 10L,
            endDateTime = DocumentObjects.dateTimeFormatString,
            shipmentType = ShipmentType.BOTH,
            user = UserInfoVo(
                id = 1L,
                profileImg = "프로필 이미지",
                nickname = "닉네임",
                isDefaultImg = true
            ),
            createAt = DocumentObjects.dateTimeFormatString
        )
        return response
    }
}
