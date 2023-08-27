package com.higoods.api.order

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.higoods.api.common.BaseControllerTest
import com.higoods.api.order.controller.OrderController
import com.higoods.api.order.dto.request.OrderAnswerDto
import com.higoods.api.order.dto.request.OrderCreateRequest
import com.higoods.api.order.dto.request.OrderOptionDto
import com.higoods.api.order.dto.response.OrderProjectsResponse
import com.higoods.api.order.dto.response.OrderResponse
import com.higoods.api.order.usecase.OrderCreateUseCase
import com.higoods.api.order.usecase.OrderReadUseCase
import com.higoods.domain.order.domain.AnswerType
import com.higoods.domain.order.domain.OrderState
import com.higoods.domain.order.domain.ReceiveType
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class OrderControllerTest : BaseControllerTest() {
    private val orderCreateUseCase: OrderCreateUseCase = mockk()
    private val orderReadUseCase: OrderReadUseCase = mockk()
    override val controller: OrderController = OrderController(orderCreateUseCase, orderReadUseCase)

    companion object {
        val orderResponse = OrderResponse(
            orderNo = "H100001", // 주문번호
            name = "이홍익", // 이름
            studentId = "B811112", // 학번
            phoneNum = "010-1111-2222", // 전화번호
            receiveType = ReceiveType.DISTRIBUTION, // 상품 수령 방법, DELIVERY or DISTRIBUTION
            refundBank = "신한은행", // 환불 은행
            refundAccount = "1234567890", // 환불 계좌
            depositName = "이홍익", // 입금자명,
            totalCost = 10000, // 총 금액
            orderOptions = listOf(OrderOptionDto(1, 2), OrderOptionDto(2, 1)),
            orderAnswers = listOf(OrderAnswerDto(1, AnswerType.SHORT, 0, "아니요"), OrderAnswerDto(2, AnswerType.MULTIPLE_CHOICE, 1, ""))

        )

        val orderCreateRequest = OrderCreateRequest(
            name = "이홍익",
            studentId = "B811112",
            phoneNum = "010-1111-2222",
            receiveType = ReceiveType.DISTRIBUTION,
            depositName = "이홍익",
            refundBank = "신한은행",
            refundAccount = "1234567890",
            totalCost = 10000,
            orderOptions = listOf(OrderOptionDto(1, 2), OrderOptionDto(2, 1)),
            orderAnswers = listOf(OrderAnswerDto(1, AnswerType.SHORT, 0, "아니요"), OrderAnswerDto(2, AnswerType.MULTIPLE_CHOICE, 1, ""))
        )
    }

    init {
//        test("주문 생성 테스트") {
//            every { orderCreateUseCase.execute(1, orderCreateRequest) } returns orderResponse
//
//            mockMvc.perform(
//                RestDocumentationRequestBuilders.post("/v1/orders/{project_id}", 1)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(orderCreateRequest))
//            )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(
//                    MockMvcRestDocumentationWrapper.document(
//                        "주문 생성 API",
//                        ResourceSnippetParametersBuilder()
//                            .description("주문 생성 API")
//                            .pathParameters(
//                                parameterWithName("project_id").description("프로젝트 id")
//                            )
//                            .requestFields(
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("studentId").type(JsonFieldType.STRING).description("학번"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("전화번호"),
//                                fieldWithPath("receiveType").type(JsonFieldType.STRING).description("상품 수령 방법"),
//                                fieldWithPath("depositName").type(JsonFieldType.STRING).description("입금자명"),
//                                fieldWithPath("refundBank").type(JsonFieldType.STRING).description("환불 은행"),
//                                fieldWithPath("refundAccount").type(JsonFieldType.STRING).description("환불 계좌"),
//                                fieldWithPath("totalCost").type(JsonFieldType.NUMBER).description("총 금액"),
//                                fieldWithPath("orderOptions[]").type(JsonFieldType.ARRAY).description("상품 옵션 정보"),
//                                fieldWithPath("orderOptions[].optionId").type(JsonFieldType.NUMBER).description("상품 옵션 id"),
//                                fieldWithPath("orderOptions[].count").type(JsonFieldType.NUMBER).description("상품 옵션 수량"),
//                                fieldWithPath("orderAnswers[]").type(JsonFieldType.ARRAY).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].optionId").type(JsonFieldType.NUMBER).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].answerType").type(JsonFieldType.STRING).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].multipleChoiceId").type(JsonFieldType.NUMBER).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].shortAnswer").type(JsonFieldType.STRING).description("커스텀 주문폼 정보").optional()
//                            )
//                            .responseFields(
//                                fieldWithPath("orderNo").type(JsonFieldType.STRING).description("주문 번호"),
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("studentId").type(JsonFieldType.STRING).description("학번"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("전화번호"),
//                                fieldWithPath("receiveType").type(JsonFieldType.STRING).description("상품 수령 방법"),
//                                fieldWithPath("depositName").type(JsonFieldType.STRING).description("입금자명"),
//                                fieldWithPath("refundBank").type(JsonFieldType.STRING).description("환불 은행"),
//                                fieldWithPath("refundAccount").type(JsonFieldType.STRING).description("환불 계좌"),
//                                fieldWithPath("totalCost").type(JsonFieldType.NUMBER).description("총 금액"),
//                                fieldWithPath("orderOptions[]").type(JsonFieldType.ARRAY).description("상품 옵션 정보"),
//                                fieldWithPath("orderOptions[].optionId").type(JsonFieldType.NUMBER).description("상품 옵션 id"),
//                                fieldWithPath("orderOptions[].count").type(JsonFieldType.NUMBER).description("상품 옵션 수량"),
//                                fieldWithPath("orderAnswers[]").type(JsonFieldType.ARRAY).description("커스텀 주문폼 정보"),
//                                fieldWithPath("orderAnswers[].optionId").type(JsonFieldType.NUMBER).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].answerType").type(JsonFieldType.STRING).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].multipleChoiceId").type(JsonFieldType.NUMBER).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].shortAnswer").type(JsonFieldType.STRING).description("커스텀 주문폼 정보").optional()
//                            )
//                    )
//                )
//        }

        test("마이페이지-내 주문 목록 조회") {
            val orderProjectResponse = listOf(
                OrderProjectsResponse(
                    orderId = 1,
                    title = "제목",
                    titleImage = "image",
                    subTitle = "부제목",
                    orderState = OrderState.PENDING
                )
            )

            every { orderReadUseCase.findAll() } returns orderProjectResponse

            mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    MockMvcRestDocumentationWrapper.document(
                        "마이페이지-내 주문 목록 조회 API",
                        ResourceSnippetParametersBuilder()
                            .description("마이페이지-내 주문 목록 조회 API")
                            .responseFields(
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("titleImage").type(JsonFieldType.STRING).description("이미지"),
                                fieldWithPath("subTitle").type(JsonFieldType.STRING).description("부제목"),
                                fieldWithPath("orderState").type(JsonFieldType.STRING).description("주문 상태")
                            )
                    )
                )
        }

//        test("내 주문 상세 조회") {
//
//            every { orderReadUseCase.findById(1) } returns orderResponse
//
//            mockMvc.perform(
//                RestDocumentationRequestBuilders.post("/v1/orders/{order_id}", 1)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//            )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(
//                    MockMvcRestDocumentationWrapper.document(
//                        "내 주문 상세 조회 API",
//                        ResourceSnippetParametersBuilder()
//                            .description("내 주문 상세 조회 API")
//                            .pathParameters(
//                                parameterWithName("order_id").description("주문 id")
//                            )
//                            .responseFields(
//                                fieldWithPath("orderNo").type(JsonFieldType.STRING).description("주문 번호"),
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("studentId").type(JsonFieldType.STRING).description("학번"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("전화번호"),
//                                fieldWithPath("receiveType").type(JsonFieldType.STRING).description("상품 수령 방법"),
//                                fieldWithPath("depositName").type(JsonFieldType.STRING).description("입금자명"),
//                                fieldWithPath("refundBank").type(JsonFieldType.STRING).description("환불 은행"),
//                                fieldWithPath("refundAccount").type(JsonFieldType.STRING).description("환불 계좌"),
//                                fieldWithPath("totalCost").type(JsonFieldType.NUMBER).description("총 금액"),
//                                fieldWithPath("orderOptions[]").description("상품 옵션 정보"),
//                                fieldWithPath("orderOptions[].optionId").type(JsonFieldType.NUMBER).description("상품 옵션 id"),
//                                fieldWithPath("orderOptions[].count").type(JsonFieldType.NUMBER).description("상품 옵션 수량"),
//                                fieldWithPath("orderAnswers[]").description("커스텀 주문폼 정보"),
//                                fieldWithPath("orderAnswers[].optionId").type(JsonFieldType.NUMBER).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].answerType").type(JsonFieldType.STRING).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].multipleChoiceId").type(JsonFieldType.NUMBER).description("커스텀 주문폼 정보").optional(),
//                                fieldWithPath("orderAnswers[].shortAnswer").type(JsonFieldType.STRING).description("커스텀 주문폼 정보").optional()
//                            )
//                    )
//                )
//        }
    }
}
