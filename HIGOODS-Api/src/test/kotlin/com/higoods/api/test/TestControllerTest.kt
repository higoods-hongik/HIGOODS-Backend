package com.higoods.api.test

import com.higoods.api.common.BaseControllerTest
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

class TestControllerTest : BaseControllerTest() {
    private val testService: TestService = mockk()
    override val controller: TestController = TestController(testService)

    init {
        test("RestDoc Test") {
            val testResponse = TestResponse(
                id = 1,
                name = "querydsl",
                currentTime = LocalDate.now()
            )
            every { testService.test("test") } returns testResponse

            val result = mockMvc.perform(
                get("/v1/test")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            result.andExpect(status().isOk())
            result.andDo(
                document(
                    "테스트 API",
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("currentTime").type(JsonFieldType.STRING).description("현재시각")
                    )
                )
            )
        }
    }
}
