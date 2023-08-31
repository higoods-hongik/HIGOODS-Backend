package com.higoods.api.order

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName
import com.epages.restdocs.apispec.ResourceDocumentation.resource
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.higoods.api.common.BaseControllerTest
import com.higoods.api.order.controller.OrderAdminController
import com.higoods.api.order.dto.response.OrderAdminResponse
import com.higoods.api.order.usecase.OrderApproveUseCase
import com.higoods.api.order.usecase.OrderCancelUseCase
import com.higoods.api.order.usecase.OrderReadUseCase
import com.higoods.domain.order.domain.OrderState
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class OrderAdminControllerTest : BaseControllerTest() {
    private val orderCancelUseCase: OrderCancelUseCase = mockk()
    private val orderApproveUseCase: OrderApproveUseCase = mockk()
    private val orderReadUseCase: OrderReadUseCase = mockk()
    override val controller: OrderAdminController = OrderAdminController(orderCancelUseCase, orderApproveUseCase, orderReadUseCase)

    init {
        test("주문 취소") {
            val orderId = 1L

            every { orderCancelUseCase.execute(orderId) } returns orderId

            mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/admins/orders/{order_id}/cancellations", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    document(
                        "[어드민] 주문 취소 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParametersBuilder()
                                .description("총대가 주문을 취소합니다.")
                                .pathParameters(
                                    parameterWithName("order_id").description("주문 id")
                                )
                                .responseFields(
                                    fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 id")
                                )
                                .build()
                        )
                    )
                )
        }

        test("입금 승인") {
            val orderId = 1L

            every { orderApproveUseCase.execute(1) } returns orderId

            mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/admins/orders/{order_id}/approvals", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    document(
                        "[어드민] 입금 승인 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParametersBuilder()
                                .description("총대가 입금을 승인합니다.")
                                .pathParameters(
                                    parameterWithName("order_id").description("주문 id")
                                )
                                .responseFields(
                                    fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 id")
                                ).build()
                        )
                    )
                )
        }

        test("명단 관리-입금 확인 목록") {
            val orderAdminResponse = PageImpl(
                listOf(
                    OrderAdminResponse(
                        orderNo = "H100001",
                        name = "이홍익",
                        depositName = "이홍익",
                        createdAt = "23.08.27 03:15",
                        phoneNum = "010-1111-2222",
                        totalCost = 10000,
                        orderState = OrderState.PENDING
                    )
                ),
                PageRequest.of(1, 6),
                10 // 전체 아이템 개수
            )

            every { orderReadUseCase.findByStateAndName(1, OrderState.PENDING, null, PageRequest.of(1, 6)) } returns orderAdminResponse

            mockMvc.perform(
                get("/v1/admins/orders/{project_id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("state", OrderState.PENDING.toString())
                    .param("name", null)
                    .param("page", "1")
                    .param("size", "6")
            )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    document(
                        "[어드민] 명단 관리-입금 확인 목록 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParametersBuilder()
                                .description("[어드민] 명단 관리-입금 확인 목록 API")
                                .pathParameters(
                                    parameterWithName("project_id").description("프로젝트 id")
                                )
                                .responseFields(
                                    fieldWithPath("content[]").description("주문 목록"),
                                    fieldWithPath("content[].orderNo").description("주문 번호"),
                                    fieldWithPath("content[].name").description("이름"),
                                    fieldWithPath("content[].depositName").description("입금자명"),
                                    fieldWithPath("content[].createdAt").description("주문 일시"),
                                    fieldWithPath("content[].phoneNum").description("전화번호"),
                                    fieldWithPath("content[].totalCost").description("총 금액"),
                                    fieldWithPath("content[].orderState").description("주문 상태"),
                                    fieldWithPath("pageable").description("페이지 정보"),
                                    fieldWithPath("totalElements").description("전체 주문 개수")
                                )
                                .build()
                        )
                    )
                )
        }
    }
}
