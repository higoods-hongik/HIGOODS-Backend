package com.higoods.api.auth.usecase.helper

import com.higoods.api.config.jwt.JwtTokenHelper
import com.higoods.api.auth.dto.response.TokenAndUserResponse
import com.higoods.common.annotation.Helper
import com.higoods.domain.user.adapter.RefreshTokenAdapter
import com.higoods.domain.user.domain.RefreshTokenRedisEntity
import com.higoods.domain.user.domain.User

@Helper
class TokenGenerateHelper(
    val jwtTokenHelper: JwtTokenHelper,
    val refreshTokenAdapter: RefreshTokenAdapter,
) {

    /**
     * jwt 토큰을 생성합니다.
     * 레디스에 리프레쉬 토큰을 ttl 값과 함께 저장합니다.
     */
    fun execute(user: User): TokenAndUserResponse {
        val newAccessToken: String = jwtTokenHelper.generateAccessToken(
            user.id,
            user.accountRole.toString(),
        )

        val newRefreshToken: String = jwtTokenHelper.generateRefreshToken(user.id)
        val newRefreshTokenEntity = RefreshTokenRedisEntity(user.id, newRefreshToken, jwtTokenHelper.refreshExpireIn)
        refreshTokenAdapter.save(newRefreshTokenEntity)

        return TokenAndUserResponse(newAccessToken, newRefreshToken, user.toUserDetailVo(), jwtTokenHelper.accessExpireIn, jwtTokenHelper.refreshExpireIn)
    }
}
