package com.higoods.domain.common.event

import java.time.LocalDateTime

open class DomainEvent {
    val publishAt: LocalDateTime = LocalDateTime.now()
}
