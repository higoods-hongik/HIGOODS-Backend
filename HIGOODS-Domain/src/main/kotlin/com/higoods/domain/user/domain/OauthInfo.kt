package com.higoods.domain.user.domain

import com.higoods.common.const.WITHDRAW_PREFIX
import java.time.LocalDateTime
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class OauthInfo(
    var oauthId: String,

    @Enumerated(EnumType.STRING)
    var oauthProvider: OauthProvider
) {

    fun withDrawOauthInfo(): OauthInfo {
        val withDrawOid = WITHDRAW_PREFIX + LocalDateTime.now().toString() + ":" + oauthId
        return OauthInfo(withDrawOid, oauthProvider)
    }
}
