package com.higoods.api.config

import org.springframework.test.context.ActiveProfilesResolver

class DomainIntegrateProfileResolver : ActiveProfilesResolver {

    override fun resolve(testClass: Class<*>): Array<String> {
        return arrayOf("test", "infra", "common", "domain")
    }
}
