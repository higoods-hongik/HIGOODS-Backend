package com.higoods.common.exception

import org.springframework.stereotype.Component

@Target(
    AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ExplainError(val value: String = "")
