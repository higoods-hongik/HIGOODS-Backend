package com.higoods.api.test

import com.higoods.api.common.BaseControllerTest
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

class TestControllerTest : BaseControllerTest() {
    private val testService: TestService = mockk()
    override val controller: TestController = TestController(testService)

    init {
        test("RestDoc Test") {
            val testResponse = TestResponse(
                id = 1,
                name = "test",
                currentTime = LocalDate.now()
            )
            every { testService.test() } returns testResponse

            val result = mockMvc.perform(
                get("/api/v1/test")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            result.andExpect(MockMvcResultMatchers.status().isOk())
                //   .andDo(MockMvcResultHandlers.print())
                .andDo(
                    document(
                        "테스트 API",
                        pathParameters(),
                        requestFields(),
                        responseFields(
                            fieldWithPath("id").type(JsonFieldType.STRING).description("id"),
                            fieldWithPath("name").type(JsonFieldType.NUMBER).description("이름"),
                            fieldWithPath("currentTime").type(JsonFieldType.STRING).description("현재시각")
                        )
                    )
                )
        }
    }
}
