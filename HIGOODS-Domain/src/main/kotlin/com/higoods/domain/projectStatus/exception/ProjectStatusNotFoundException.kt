package com.higoods.domain.projectStatus.exception

import com.higoods.common.exception.HiGoodsCodeException

class ProjectStatusNotFoundException : HiGoodsCodeException(
    ProjectStatusErrorCode.PROJECT_STATUS_NOT_FOUND
) {
    companion object {
        val EXCEPTION: HiGoodsCodeException = ProjectStatusNotFoundException()
    }
}
