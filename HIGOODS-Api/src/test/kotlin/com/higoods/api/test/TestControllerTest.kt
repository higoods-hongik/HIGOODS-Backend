package com.higoods.api.test

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceDocumentation.resource
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.higoods.api.common.BaseControllerTest
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
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

            mockMvc.perform(
                get("/v1/test")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk())
                .andDo(
                    document(
                        "테스트 API",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                            ResourceSnippetParametersBuilder()
                                .description("이번엔 되겠지")
                                .tag("테스트")
                                .responseFields(
                                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                    fieldWithPath("currentTime").type(JsonFieldType.STRING).description("현재시각")
                                )
                                .build()
                        )
                    )
                )
        }
    }
}
