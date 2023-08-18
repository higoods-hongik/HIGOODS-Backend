// package com.higoods.api.order
//
// import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
// import com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName
// import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
// import com.higoods.api.common.BaseControllerTest
// import com.higoods.api.order.controller.OrderController
// import com.higoods.api.order.dto.request.OrderAnswerDto
// import com.higoods.api.order.dto.request.OrderCreateRequest
// import com.higoods.api.order.dto.request.OrderOptionDto
// import com.higoods.api.order.dto.response.OrderResponse
// import com.higoods.api.order.usecase.OrderCreateUseCase
// import com.higoods.domain.order.domain.AnswerType
// import com.higoods.domain.order.domain.ReceiveType
// import io.mockk.every
// import io.mockk.mockk
// import org.springframework.http.MediaType
// import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
// import org.springframework.restdocs.payload.JsonFieldType
// import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//
// class OrderControllerTest : BaseControllerTest() {
//    private val orderCreateUseCase: OrderCreateUseCase = mockk()
//    override val controller: OrderController = OrderController(orderCreateUseCase)
//
//    init {
//        test("주문 생성 테스트") {
//            val orderResponse = OrderResponse(
//                orderNo = "H100001", // 주문번호
//                name = "이홍익", // 이름
//                studentId = "B811112", // 학번
//                phoneNum = "01011112222", // 전화번호
//                receiveType = ReceiveType.DISTRIBUTION, // 상품 수령 방법, DELIVERY or DISTRIBUTION
//                refundBank = "신한은행", // 환불 은행
//                refundAccount = "1234567890", // 환불 계좌
//                depositName = "이홍익", // 입금자명,
//                totalCost = 10000, // 총 금액
//                orderOptions = listOf(OrderOptionDto(1, 2), OrderOptionDto(2, 1)),
//                orderAnswers = listOf(OrderAnswerDto(1, AnswerType.SHORT, 0, "아니요"), OrderAnswerDto(2, AnswerType.MULTIPLE_CHOICE, 1, ""))
//
//            )
//
//            val orderCreateRequest = OrderCreateRequest(
//                name = "이홍익",
//                studentId = "B811112",
//                phoneNum = "01011112222",
//                receiveType = ReceiveType.DISTRIBUTION,
//                depositName = "이홍익",
//                refundBank = "신한은행",
//                refundAccount = "1234567890",
//                totalCost = 10000,
//                orderOptions = listOf(OrderOptionDto(1, 2), OrderOptionDto(2, 1)),
//                orderAnswers = listOf(OrderAnswerDto(1, AnswerType.SHORT, 0, "아니요"), OrderAnswerDto(2, AnswerType.MULTIPLE_CHOICE, 1, ""))
//            )
//
//            every { orderCreateUseCase.execute(1, orderCreateRequest) } returns orderResponse
//
//            mockMvc.perform(
//                RestDocumentationRequestBuilders.post("/v1/orders/{project_id}",1)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//            )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(
//                    MockMvcRestDocumentationWrapper.document(
//                        "주문 생성 API",
//                        ResourceSnippetParametersBuilder()
//                            .description("유저 주문을 생성합니다.")
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
//                                fieldWithPath("orderOptions[]").description("상품 옵션 정보"),
//                                fieldWithPath("orderOptions[].optionId").type(JsonFieldType.NUMBER).description("상품 옵션 id"),
//                                fieldWithPath("orderOptions[].count").type(JsonFieldType.NUMBER).description("상품 옵션 수량"),
//                                fieldWithPath("orderAnswers[]").description("커스텀 주문폼 정보").optional(),
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
//    }
// }
