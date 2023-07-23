package com.higoods.api.auth.dto.response

import com.higoods.domain.common.vo.UserDetailVo

data class TokenAndUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDetailVo,
    val accessTokenExpireIn: Long,
    val refreshTokenExpireIn: Long
)
