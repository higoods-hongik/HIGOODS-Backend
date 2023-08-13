package com.higoods.domain.project.exception

import com.higoods.common.exception.HiGoodsCodeException

class ProjectNotFoundException : HiGoodsCodeException(
    ProjectErrorCode.PROJECT_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ProjectNotFoundException()
    }
}
