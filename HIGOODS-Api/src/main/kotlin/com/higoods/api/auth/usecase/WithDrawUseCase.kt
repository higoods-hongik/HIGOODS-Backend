package com.higoods.api.auth.usecase

import com.higoods.api.auth.helper.KakaoOauthHelper
import com.higoods.api.config.security.SecurityUtils
import com.higoods.common.annotation.UseCase
import com.higoods.domain.user.adapter.RefreshTokenAdapter
import com.higoods.domain.user.adapter.UserAdapter
import com.higoods.domain.user.service.UserDomainService
import org.springframework.transaction.annotation.Transactional

@UseCase
class WithDrawUseCase(
    val refreshTokenAdapter: RefreshTokenAdapter,
    val userAdapter: UserAdapter,
    val userDomainService: UserDomainService,
    val kakaoOauthHelper: KakaoOauthHelper
) {

    @Transactional
    fun execute() {
        val currentUserId: Long = SecurityUtils.currentUserId
        refreshTokenAdapter.deleteByUserId(currentUserId)
        val user = userAdapter.queryUser(currentUserId)
        val oid = user.oauthInfo.oauthId
        userDomainService.withDrawUser(currentUserId)
        kakaoOauthHelper.unlink(oid)
    }
}
