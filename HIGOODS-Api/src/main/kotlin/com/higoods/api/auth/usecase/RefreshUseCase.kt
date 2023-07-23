package com.higoods.api.auth.usecase

import com.higoods.api.auth.dto.response.TokenAndUserResponse
import com.higoods.api.auth.usecase.helper.TokenGenerateHelper
import com.higoods.api.config.jwt.JwtTokenHelper
import com.higoods.common.annotation.UseCase
import com.higoods.domain.user.adapter.RefreshTokenAdapter
import com.higoods.domain.user.adapter.UserAdapter
import com.higoods.domain.user.domain.User
import com.higoods.domain.user.service.UserDomainService

@UseCase
class RefreshUseCase(
    val refreshTokenAdapter: RefreshTokenAdapter,
    val jwtTokenHelper: JwtTokenHelper,
    val userAdapter: UserAdapter,
    val userDomainService: UserDomainService,
    val tokenGenerateHelper: TokenGenerateHelper
) {

    /**
     * 토큰 리프레쉬시 실행됩니다.
     * 리프레쉬토큰도 다시 새로 뽑아서 내려줍니다.
     */
    fun execute(refreshToken: String): TokenAndUserResponse {
        refreshTokenAdapter.queryRefreshToken(refreshToken)
        val refreshUserId: Long =
            jwtTokenHelper.parseRefreshToken(refreshToken)
        val user: User = userAdapter.queryUser(refreshUserId)
        // 리프레쉬 시에도 last로그인 정보 업데이트
        userDomainService.refreshUser(user.oauthInfo)
        return tokenGenerateHelper.execute(user) // 리프레쉬 토큰도 새로고침해서 내려줍니다.
    }
}
