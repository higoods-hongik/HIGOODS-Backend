package com.higoods.common.annotation.annotation

import com.higoods.common.exception.BaseErrorCode
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorCodeExample(val value: KClass<out BaseErrorCode>)
