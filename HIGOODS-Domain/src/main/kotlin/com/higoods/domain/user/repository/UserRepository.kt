package com.higoods.domain.user.repository

import com.higoods.domain.user.domain.OauthInfo
import com.higoods.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByOauthInfo(oauthInfo: OauthInfo): User?

    fun findAllByNickname(name: String): List<User>?
}
