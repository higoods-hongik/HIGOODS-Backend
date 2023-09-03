package com.higoods.domain.project.service

import com.higoods.common.annotation.DomainService
import com.higoods.domain.common.lock.RedissonLock
import com.higoods.domain.project.adapter.ProjectAdapter
import com.higoods.domain.project.domain.Project
import org.springframework.transaction.annotation.Transactional

@DomainService
class ProjectDomainService(
    private val projectAdapter: ProjectAdapter
) {

    @Transactional
    fun create(project: Project): Long {
        return projectAdapter.save(project).id
    }

    @Transactional
    fun update(projectId: Long, content: String, subTitle: String, titleImage: String): Long {
        val project = projectAdapter.queryById(projectId)
        project.update(
            content,
            subTitle,
            titleImage
        )
        return projectId
    }

    @RedissonLock(key = "#projectId", lockName = "구매 인원 증가")
    fun increasePurchaseNum(projectId: Long) {
        val project = projectAdapter.queryById(projectId)
        project.increaseCurrentPurchaseQuantity()
    }

    @RedissonLock(key = "#projectId", lockName = "구매 인원 감소")
    fun decreasePurchaseNum(project: Project) {
        project.decreaseCurrentPurchaseQuantity()
    }
}
