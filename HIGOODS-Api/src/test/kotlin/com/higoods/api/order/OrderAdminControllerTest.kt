package com.higoods.api.order

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName
import com.epages.restdocs.apispec.ResourceDocumentation.resource
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.epages.restdocs.apispec.Schema
import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.DocumentObjects
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class OrderAdminControllerTest : BaseControllerTest() {
    private val orderCancelUseCase: OrderCancelUseCase = mockk()
    private val orderApproveUseCase: OrderApproveUseCase = mockk()
    private val orderReadUseCase: OrderReadUseCase = mockk()
    override val controller: OrderAdminController = OrderAdminController(orderCancelUseCase, orderApproveUseCase, orderReadUseCase)

    init {
        test("주문 취소") {
            val orderAdminResponse =
                OrderAdminResponse(
                    orderNo = "H100001",
                    name = "이홍익",
                    depositName = "이홍익",
                    createdAt = DocumentObjects.dateTimeFormatString,
                    phoneNum = "010-1111-2222",
                    totalCost = 10000,
                    orderState = OrderState.PENDING
                )

            every { orderCancelUseCase.execute(1) } returns orderAdminResponse

            post("/v1/admins/orders/{order_id}/cancellations", arrayOf("1")) {
                contentType(MediaType.APPLICATION_JSON)
                accept(MediaType.APPLICATION_JSON)
                authorizationHeader(1)
            }
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    document(
                        "[어드민] 주문 취소 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParametersBuilder()
                                .description("[어드민] 주문 취소 API")
                                .tag("[어드민] 주문")
                                .pathParameters(
                                    parameterWithName("order_id").description("주문 id")
                                )
                                .responseFields(
                                    fieldWithPath("orderNo").type(JsonFieldType.STRING).description("주문 번호"),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                    fieldWithPath("depositName").type(JsonFieldType.STRING).description("입금자명"),
                                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("주문 일시"),
                                    fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("전화번호"),
                                    fieldWithPath("totalCost").type(JsonFieldType.NUMBER).description("총 금액"),
                                    fieldWithPath("orderState").type(JsonFieldType.STRING).description("주문 상태 이넘 PENDING,APPROVAL,CANCELED")
                                )
                                .responseSchema(Schema.schema("[어드민] 주문 취소 Res"))
                                .build()
                        )
                    )
                )
        }

        test("입금 승인") {
            val orderAdminResponse =
                OrderAdminResponse(
                    orderNo = "H100001",
                    name = "이홍익",
                    depositName = "이홍익",
                    createdAt = DocumentObjects.dateTimeFormatString,
                    phoneNum = "010-1111-2222",
                    totalCost = 10000,
                    orderState = OrderState.PENDING
                )

            every { orderApproveUseCase.execute(1) } returns orderAdminResponse

            post("/v1/admins/orders/{order_id}/approvals", arrayOf("1")) {
                contentType(MediaType.APPLICATION_JSON)
                accept(MediaType.APPLICATION_JSON)
                authorizationHeader(1)
            }
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    document(
                        "[어드민] 입금 승인 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParametersBuilder()
                                .description("총대가 입금을 승인합니다.")
                                .tag("[어드민] 주문")
                                .pathParameters(
                                    parameterWithName("order_id").description("주문 id")
                                )
                                .responseFields(
                                    fieldWithPath("orderNo").type(JsonFieldType.STRING).description("주문 번호"),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                    fieldWithPath("depositName").type(JsonFieldType.STRING).description("입금자명"),
                                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("주문 일시"),
                                    fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("전화번호"),
                                    fieldWithPath("totalCost").type(JsonFieldType.NUMBER).description("총 금액"),
                                    fieldWithPath("orderState").type(JsonFieldType.STRING).description("주문 상태 이넘 PENDING,APPROVAL,CANCELED")
                                )
                                .responseSchema(Schema.schema("[어드민] 입금 승인 Res"))
                                .build()
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
                        createdAt = DocumentObjects.dateTimeFormatString,
                        phoneNum = "010-1111-2222",
                        totalCost = 10000,
                        orderState = OrderState.PENDING
                    )
                )
            )

            every { orderReadUseCase.findByStateAndName(1, OrderState.PENDING, null, PageRequest.of(1, 6)) } returns orderAdminResponse

            get("/v1/admins/orders/{project_id}", arrayOf("1")) {
                contentType(MediaType.APPLICATION_JSON)
                accept(MediaType.APPLICATION_JSON)
                param("state", OrderState.PENDING.toString())
                param("name", null)
                param("page", "1")
                param("size", "6")
                authorizationHeader(1)
            }
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    document(
                        "[어드민] 명단 관리-입금 확인 목록 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParametersBuilder()
                                .description("[어드민] 명단 관리-입금 확인 목록 API")
                                .tag("[어드민] 주문")
                                .pathParameters(
                                    parameterWithName("project_id").description("프로젝트 id")
                                )
                                .requestParameters(
                                    parameterWithName("state").description("state 이넘 PENDING,APPROVAL,CANCELED"),
                                    parameterWithName("name").description("name").optional(),
                                    parameterWithName("page").description("page"),
                                    parameterWithName("size").description("size")
                                )
                                .responseFields(
                                    fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("주문 목록"),
                                    fieldWithPath("content[].orderNo").type(JsonFieldType.STRING).description("주문 번호"),
                                    fieldWithPath("content[].name").type(JsonFieldType.STRING).description("이름"),
                                    fieldWithPath("content[].depositName").type(JsonFieldType.STRING).description("입금자명"),
                                    fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("주문 일시"),
                                    fieldWithPath("content[].phoneNum").type(JsonFieldType.STRING).description("전화번호"),
                                    fieldWithPath("content[].totalCost").type(JsonFieldType.NUMBER).description("총 금액"),
                                    fieldWithPath("content[].orderState").type(JsonFieldType.STRING).description("주문 상태 이넘 PENDING,APPROVAL,CANCELED"),
                                    fieldWithPath("pageable").description("페이지 정보"),
                                    fieldWithPath("totalElements").description("전체 주문 개수"),
                                    fieldWithPath("totalPages").description("전체 페이지 수"),
                                    fieldWithPath("last").description("마지막 페이지 여부"),
                                    fieldWithPath("number").description("현재 페이지 번호"),
                                    fieldWithPath("size").description("페이지 크기"),
                                    fieldWithPath("sort.empty").description("정렬 필드가 비어있는지 여부"),
                                    fieldWithPath("sort.unsorted").description("정렬된 상태 여부"),
                                    fieldWithPath("sort.sorted").description("정렬되지 않은 상태 여부"),
                                    fieldWithPath("first").description("첫 페이지 여부"),
                                    fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                                    fieldWithPath("empty").description("데이터 비어있음 여부")
                                )
                                .responseSchema(Schema.schema("[어드민] 명단 관리-입금 확인 목록 Res"))
                                .build()
                        )
                    )
                )
        }
    }
}
