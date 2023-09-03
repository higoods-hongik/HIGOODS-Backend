package com.higoods.api.common

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.higoods.api.config.security.AuthDetails
import io.kotest.core.spec.style.FunSpec
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockPart
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.RequestHeadersSnippet
import org.springframework.restdocs.headers.ResponseHeadersSnippet
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.restdocs.request.RequestParametersSnippet
import org.springframework.restdocs.snippet.Snippet
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

abstract class BaseControllerTest : FunSpec() {
    protected abstract val controller: Any
    protected lateinit var mockMvc: MockMvc
    private val restDocumentation = ManualRestDocumentation()

    companion object {
        @JvmStatic
        protected val objectMapper: ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
    }

    init {
        beforeSpec {
            setUpMockMvc()
        }
        beforeEach {
            restDocumentation.beforeTest(javaClass, it.name.testName)
        }
        afterEach {
            restDocumentation.afterTest()
        }
    }

    private fun setUpMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .apply<StandaloneMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                    .uris().withScheme("http").withHost("localhost").and()
                    .operationPreprocessors()
            )
            .build()
    }

    protected fun get(url: String, buildRequest: MockHttpServletRequestBuilder.() -> Unit): ResultActions =
        mockMvc.perform(get(url).apply(buildRequest))

    protected fun get(
        url: String,
        pathParams: Array<Any> = emptyArray(),
        buildRequest: MockHttpServletRequestBuilder.() -> Unit
    ): ResultActions =
        mockMvc.perform(get(url, *pathParams).apply(buildRequest))

    protected fun post(
        url: String,
        pathParams: Array<Any> = emptyArray(),
        buildRequest: MockHttpServletRequestBuilder.() -> Unit
    ): ResultActions =
        mockMvc.perform(post(url, *pathParams).apply(buildRequest))

    protected fun post(
        url: String,
        request: Any,
        pathParams: Array<Any> = emptyArray(),
        buildRequest: MockHttpServletRequestBuilder.() -> Unit
    ): ResultActions =
        mockMvc.perform(
            post(url, *pathParams).apply {
                contentType(MediaType.APPLICATION_JSON)
                content(objectMapper.writeValueAsString(request))
                buildRequest()
            }
        )

    protected fun multipart(
        url: String,
        mockFiles: List<MockMultipartFile>,
        mockParts: List<MockPart>,
        buildRequest: MockHttpServletRequestBuilder.() -> Unit
    ): ResultActions {
        return mockMvc.perform(
            RestDocumentationRequestBuilders.multipart(url)
                .apply {
                    mockFiles.forEach { this.file(it) }
                    mockParts.forEach { this.part(it) }
                    buildRequest()
                }
        )
    }

    protected fun delete(
        url: String,
        pathParams: Array<Any> = emptyArray(),
        buildRequest: MockHttpServletRequestBuilder.() -> Unit
    ): ResultActions =
        mockMvc.perform(delete(url, *pathParams).apply(buildRequest))

    protected fun patch(
        url: String,
        pathParams: Array<Any> = emptyArray(),
        buildRequest: MockHttpServletRequestBuilder.() -> Unit
    ): ResultActions =
        mockMvc.perform(patch(url, *pathParams).apply(buildRequest))

    protected fun put(
        url: String,
        request: Any,
        pathParams: Array<Any> = emptyArray(),
        buildRequest: MockHttpServletRequestBuilder.() -> Unit
    ): ResultActions =
        mockMvc.perform(
            put(url, *pathParams)
                .apply {
                    contentType(MediaType.APPLICATION_JSON)
                    content(objectMapper.writeValueAsString(request))
                    buildRequest()
                }
        )

    protected fun MockHttpServletRequestBuilder.authorizationHeader(userId: Long) {
        val authDetails = AuthDetails(userId.toString(), "ROLE_USER")
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            authDetails,
            "user",
            authDetails.authorities
        )
        // 실제 토큰을 집어넣는이유.
        // https://github.com/ePages-de/restdocs-api-spec/pull/86
        // https://github.com/ePages-de/restdocs-api-spec/blob/4c735ca2796d0620ecef30e20471a173cec0809b/restdocs-api-spec/src/main/kotlin/com/epages/restdocs/apispec/JwtSecurityHandler.kt#L14
        // 복호화해서 scope 들어있는 oauth 헤더가 아니면 자동으로 좌물쇠 채워주는 소스가 있습니다.
        this.header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    }

    protected fun ResultActions.isStatus(code: Int): ResultActions =
        andExpect(status().`is`(code))

    protected fun ResultActions.makeDocument(
        documentInfo: DocumentInfo,
        vararg snippets: Snippet
    ): ResultActions =
        andDo(
            document(
                identifier = documentInfo.identifier,
                resourceDetails = ResourceSnippetParametersBuilder()
                    .description(documentInfo.description)
                    .deprecated(documentInfo.deprecated)
                    .tag(documentInfo.tag.value),
                snippets = snippets
            )
        )

    protected infix fun String.type(fieldType: DocumentFieldType): DocumentField {
        return DocumentField(this, fieldType.type)
    }

    protected infix fun <T : Enum<T>> String.type(fieldType: ENUM<T>): DocumentField {
        return DocumentField(this, fieldType.type, fieldType.enums)
    }

    protected infix fun String.means(description: String): DocumentField =
        DocumentField(this).apply {
            means(description)
        }

    protected fun requestHeaders(vararg fields: DocumentField): RequestHeadersSnippet =
        HeaderDocumentation.requestHeaders(
            fields.map(DocumentField::toHeaderDescriptor).toList()
        )

    protected fun responseHeaders(vararg fields: DocumentField): ResponseHeadersSnippet =
        HeaderDocumentation.responseHeaders(
            fields.map(DocumentField::toHeaderDescriptor).toList()
        )

    protected fun pathParameters(vararg fields: DocumentField): PathParametersSnippet =
        pathParameters(
            fields.map(DocumentField::toParameterDescriptor).toList()
        )

    protected fun queryParameters(vararg fields: DocumentField): RequestParametersSnippet =
        requestParameters(
            fields.map(DocumentField::toParameterDescriptor).toList()
        )

    protected fun requestFields(vararg fields: DocumentField): RequestFieldsSnippet =
        requestFields(
            fields.map(DocumentField::toFieldDescriptor).toList()
        )

    protected fun responseFields(vararg fields: DocumentField): ResponseFieldsSnippet =
        responseFields(
            fields.map(DocumentField::toFieldDescriptor).toList()
        )

    protected data class DocumentInfo(
        val identifier: String,
        val tag: OpenApiTag,
        val description: String? = null,
        val deprecated: Boolean = false
    )
}
