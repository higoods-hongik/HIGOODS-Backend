package com.higoods.api.orderform

import com.higoods.api.common.ARRAY
import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.ENUM
import com.higoods.api.common.NUMBER
import com.higoods.api.common.OpenApiTag
import com.higoods.api.common.STRING
import com.higoods.api.orderform.controller.OrderFormController
import com.higoods.api.orderform.dto.request.OrderFormCreateRequest
import com.higoods.api.orderform.usecase.OrderFormCreateUseCase
import com.higoods.api.orderform.usecase.OrderFormReadUseCase
import com.higoods.domain.orderform.domain.MultipleChoiceContent
import com.higoods.domain.orderform.domain.OrderFormContent
import com.higoods.domain.orderform.domain.OrderFormType
import com.higoods.domain.orderform.domain.OrderFormVo
import io.mockk.every
import io.mockk.mockk

class OrderFormControllerTest : BaseControllerTest() {
    private val orderFormCreateUseCase: OrderFormCreateUseCase = mockk()
    private val orderFormReadUseCase: OrderFormReadUseCase = mockk()

    override val controller: OrderFormController = OrderFormController(
        orderFormCreateUseCase = orderFormCreateUseCase,
        orderFormReadUseCase = orderFormReadUseCase
    )

    init {
        test("POST /v1/project/{project_id}/order-form") {

            every { orderFormCreateUseCase.execute(any(), any()) } returns orderFormVo()

            val request = listOf(
                OrderFormCreateRequest(
                    type = OrderFormType.MULTIPLE_CHOICE,
                    question = "객관식",
                    choices = listOf("선택지 1번", "선택지 2번")
                ),
                OrderFormCreateRequest(
                    type = OrderFormType.SHORT_FORM,
                    question = "주관식"
                )
            )

            post("/v1/project/{project_id}/order-form", pathParams = arrayOf(1), request = request) {
                authorizationHeader(1)
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "프로젝트의 주문 폼 생성",
                        tag = OpenApiTag.ORDER_FORM,
                        description = "프로젝트의 주문 폼 생성시 요청합니다."
                    ),
                    pathParameters("project_id" means "프로젝트 아이디"),
                    requestFields(
                        "[]" type ARRAY means "OrderFormCreateRequest",
                        "[].question" type STRING means "질문 타이틀",
                        "[].type" type ENUM(OrderFormType::class) means "OrderFormType(MULTIPLE_CHOICE,SHORT_FORM)",
                        "[].choices" type ARRAY means "String[]"
                    ),
                    responseFields(
                        "projectId" type NUMBER means "프로젝트 아이디",
                        "orderFormId" type NUMBER means "orderFormId 아이디",
                        "content[].type" type STRING means "OrderFormType(MULTIPLE_CHOICE,SHORT_FORM)",
                        "content[].question" type STRING means "질문 타이틀",
                        "content[].choices" type ARRAY means "선택지 배열 ( 객관식만 )",
                        "content[].choices[].id" type NUMBER means "객관식 선택지 아이디",
                        "content[].choices[].optionDisplayName" type STRING means "객관식 선택지 이름"
                    )
                )
        }

        test("GET /v1/project/{project_id}/items") {

            every { orderFormReadUseCase.execute(any()) } returns orderFormVo()

            get("/v1/project/{project_id}/order-form", pathParams = arrayOf(1)) {
                authorizationHeader(1)
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "프로젝트의 주문 폼 조회",
                        tag = OpenApiTag.ORDER_FORM,
                        description = "프로젝트의 주문 폼 조회시 요청합니다."
                    ),
                    pathParameters("project_id" means "프로젝트 아이디"),
                    responseFields(
                        "projectId" type NUMBER means "프로젝트 아이디",
                        "orderFormId" type NUMBER means "orderFormId 아이디",
                        "content[].type" type STRING means "OrderFormType(MULTIPLE_CHOICE,SHORT_FORM)",
                        "content[].question" type STRING means "질문 타이틀",
                        "content[].choices" type ARRAY means "선택지 배열 ( 객관식만 )",
                        "content[].choices[].id" type NUMBER means "객관식 선택지 아이디",
                        "content[].choices[].optionDisplayName" type STRING means "객관식 선택지 이름"
                    )
                )
        }
    }

    private fun orderFormVo(): OrderFormVo {
        return OrderFormVo(
            projectId = 1,
            orderFormId = 20,
            content = listOf(
                OrderFormContent(
                    type = OrderFormType.SHORT_FORM,
                    question = "이건 주관식 질문이고요. choices 는 빈배열로 내려옵니다."
                ),
                OrderFormContent(
                    type = OrderFormType.MULTIPLE_CHOICE,
                    question = "이건 객관식 폼이구요",
                    choices = listOf(
                        MultipleChoiceContent(
                            id = 101,
                            optionDisplayName = "1번 뭘 선택 해주세요 어쩌구"
                        ),
                        MultipleChoiceContent(
                            id = 102,
                            optionDisplayName = "2번 뭘 선택 해주세요 어쩌구"
                        )
                    )

                )
            )

        )
    }
}
