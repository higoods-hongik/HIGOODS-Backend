package com.higoods.api.distribution

import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.DocumentObjects
import com.higoods.api.common.DocumentObjects.getPageResponse
import com.higoods.api.common.NUMBER
import com.higoods.api.common.OpenApiTag
import com.higoods.api.common.STRING
import com.higoods.api.distribution.controller.DistributionAdminController
import com.higoods.api.distribution.dto.response.DistributionResponse
import com.higoods.api.distribution.usecase.DistributionReadUseCase
import com.higoods.domain.distribution.domain.DistributionType
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType

class DistributionAdminControllerTest : BaseControllerTest() {
    private val distributionReadUseCase: DistributionReadUseCase = mockk()
    override val controller: DistributionAdminController = DistributionAdminController(distributionReadUseCase)

    private fun distributionResponse(): DistributionResponse {
        return DistributionResponse(
            orderNo = "H100001",
            name = "홍길동",
            orderDate = DocumentObjects.dateTimeFormatString,
            phoneNumber = "010-1111-2222",
            receiveDate = DocumentObjects.dateTimeFormatString,
            distributionType = DistributionType.NOT_RECEIVED
        )
    }
    init {
        test("현장 배부 목록 조회") {

            every { distributionReadUseCase.findAll(any(), any(), any()) } returns PageImpl(listOf(distributionResponse()))

            get("/v1/admins/distributions/{project_id}", pathParams = arrayOf("1")) {
                queryParam("page", "10")
                queryParam("size", "10")
                contentType(MediaType.APPLICATION_JSON)
                accept(MediaType.APPLICATION_JSON)
                authorizationHeader(1)
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "[어드민] 현장 배부 목록 조회",
                        tag = OpenApiTag.DISTRIBUTION,
                        description = "[어드민] 현장 배부 목록 조회 API, 현장 배부 목록을 조회합니다."
                    ),
                    pathParameters(
                        "project_id" type NUMBER means "프로젝트 아이디"
                    ),
                    queryParameters(
                        "page" type NUMBER means "페이지",
                        "size" type NUMBER means "사이즈",
                        "name" type STRING means "이름" isOptional(true)
                    ),
                    getPageResponse(
                        arrayOf(
                            "orderNo" type STRING means "주문번호",
                            "name" type STRING means "이름",
                            "orderDate" type STRING means "주문 일시",
                            "phoneNumber" type STRING means "전화번호",
                            "receiveDate" type STRING means "수령한 날짜",
                            "distributionType" type STRING means "상태 enum NOT_RECEIVED, RECEIVED"
                        )
                    )
                )
        }
    }
}