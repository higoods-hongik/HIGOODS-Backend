package com.higoods.infra.api.kakao.dto

data class OIDCPublicKeyDto(

    val kid: String,
    val alg: String,
    val use: String,
    val n: String,
    val e: String,
)
