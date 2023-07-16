package com.higoods.api.test.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.annotation.MustBeDocumented
import kotlin.annotation.Retention

/** API 모듈의 통합테스트의 편의성을 위해서 만든 어노테이션 -이찬진  */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [ApiIntegrateTestConfig::class])
@ActiveProfiles(resolver = ApiIntegrateProfileResolver::class)
@MustBeDocumented
annotation class ApiIntegrateSpringBootTest
