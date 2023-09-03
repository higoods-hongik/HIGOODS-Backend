package com.higoods.api.project

import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.DocumentObjects
import com.higoods.api.common.ENUM
import com.higoods.api.common.NUMBER
import com.higoods.api.common.OpenApiTag
import com.higoods.api.common.STRING
import com.higoods.api.project.controller.ProjectController
import com.higoods.api.project.dto.request.ProjectCreateRequest
import com.higoods.api.project.dto.response.ProjectResponse
import com.higoods.api.project.usecase.ProjectCreateUseCase
import com.higoods.api.project.usecase.ProjectReadUseCase
import com.higoods.api.project.usecase.ProjectUpdateUseCase
import com.higoods.domain.common.vo.UserInfoVo
import com.higoods.domain.project.domain.ShipmentType
import io.mockk.every
import io.mockk.mockk

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
        test("/v1/projects") {

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

            every { projectCreateUseCase.execute(any()) } returns response

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
                    responseFields(
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
                )
        }
    }
}
