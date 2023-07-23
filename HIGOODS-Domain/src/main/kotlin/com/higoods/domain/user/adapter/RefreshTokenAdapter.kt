package com.higoods.domain.user.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.common.exception.custom.RefreshTokenExpiredException
import com.higoods.domain.user.domain.RefreshTokenRedisEntity
import com.higoods.domain.user.repository.RefreshTokenRepository

@Adapter
class RefreshTokenAdapter(
    val refreshTokenRepository: RefreshTokenRepository
) {

    fun queryRefreshToken(refreshToken: String): RefreshTokenRedisEntity {
        return refreshTokenRepository
            .findByRefreshToken(refreshToken) ?: run { throw RefreshTokenExpiredException.EXCEPTION }
    }

    fun save(refreshToken: RefreshTokenRedisEntity): RefreshTokenRedisEntity {
        return refreshTokenRepository.save(refreshToken)
    }

    fun deleteByUserId(userId: Long) {
        refreshTokenRepository.deleteById(userId.toString())
    }
}
