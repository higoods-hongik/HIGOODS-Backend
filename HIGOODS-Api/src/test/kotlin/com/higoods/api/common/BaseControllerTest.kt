package com.higoods.api.common

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.FunSpec
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

abstract class BaseControllerTest : FunSpec() {
    protected abstract val controller: Any
    protected lateinit var mockMvc: MockMvc
    private val restDocumentation = ManualRestDocumentation()

    companion object {
        @JvmStatic
        protected val objectMapper = ObjectMapper()
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
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
            )
            .build()
    }
}
