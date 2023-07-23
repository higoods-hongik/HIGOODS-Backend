package com.higoods.domain.user.repository

import com.higoods.domain.user.domain.RefreshTokenRedisEntity
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshTokenRedisEntity, String> {

    fun findByRefreshToken(refreshToken: String): RefreshTokenRedisEntity?
}
