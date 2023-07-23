package com.higoods.domain.events

import com.higoods.domain.common.event.DomainEvent

data class UserRegisterEvent(
    val userId: Long
) : DomainEvent()
