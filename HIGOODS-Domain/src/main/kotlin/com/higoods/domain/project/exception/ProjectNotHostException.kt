package com.higoods.domain.project.exception

import com.higoods.common.exception.HiGoodsCodeException

class ProjectNotHostException : HiGoodsCodeException(
    ProjectErrorCode.PROJECT_NOT_HOST
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ProjectNotHostException()
    }
}
