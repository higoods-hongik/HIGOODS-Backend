package com.higoods.api.config.jwt

data class AccessTokenInfo(
    val userId: Long,
    val role: String,
)
