package com.higoods.domain.test.entity
import com.higoods.domain.common.BaseEntity
import mu.KLogging
import javax.persistence.Entity

@Entity
data class TestEntity(
    val name: String = "test"
) : BaseEntity() {
    fun loggingTest() {
        val log = KLogging().logger
        log.info { "info" }
        log.warn { "warn" }
        log.error { "error" }
    }
}
