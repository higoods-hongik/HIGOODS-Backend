package com.higoods.domain.user.adapter

import com.higoods.common.annotation.Adapter
import com.higoods.domain.user.domain.OauthInfo
import com.higoods.domain.user.domain.User
import com.higoods.domain.user.exception.UserNotFoundException
import com.higoods.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class UserAdapter(
    val userRepository: UserRepository
) {

    fun queryUser(userId: Long): User {
        return userRepository.findByIdOrNull(userId) ?: run { throw UserNotFoundException.EXCEPTION }
    }

    fun queryUsers(userIds: List<Long>): List<User> {
        return userRepository.findAllById(userIds)
    }

    fun queryByOauthInfo(oauthInfo: OauthInfo): User {
        return userRepository.findByOauthInfo(oauthInfo) ?: run { throw UserNotFoundException.EXCEPTION }
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }
}
