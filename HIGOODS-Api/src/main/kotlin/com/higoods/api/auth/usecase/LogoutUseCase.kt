package com.higoods.api.auth.usecase

import com.higoods.api.config.security.SecurityUtils
import com.higoods.common.annotation.UseCase
import com.higoods.domain.user.adapter.RefreshTokenAdapter


@UseCase
class LogoutUseCase(
    val refreshTokenAdapter: RefreshTokenAdapter,
) {
    fun execute() {
        val currentUserId: Long = SecurityUtils.currentUserId
        refreshTokenAdapter.deleteByUserId(currentUserId)
    }
}
