package com.higoods.domain.common.lock

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedissonLock(
    val key: String,
    val lockName: String,
    val waitTime: Long = 30L,
    val leaseTime: Long = 10L,
    val timeUnit: TimeUnit = TimeUnit.SECONDS
)
