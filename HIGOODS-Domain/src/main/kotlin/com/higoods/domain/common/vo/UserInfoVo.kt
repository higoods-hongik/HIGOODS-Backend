package com.higoods.domain.common.vo

import com.higoods.domain.user.domain.User

class UserInfoVo(
    val id: Long,
    val profileImg: String,
    val nickname: String,
    val isDefaultImg: Boolean
) {
    companion object {
        fun from(user: User): UserInfoVo {
            return UserInfoVo(user.id, user.profileImg, user.nickname, user.isDefaultImg)
        }
    }
}
