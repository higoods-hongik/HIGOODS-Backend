package com.higoods.domain.project.exception

import com.higoods.common.exception.HiGoodsCodeException

class ProjectNotItemCreateStatusException : HiGoodsCodeException(
    ProjectErrorCode.PROJECT_NOT_ITEM_CREATE_STATUS
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ProjectNotItemCreateStatusException()
    }
}
