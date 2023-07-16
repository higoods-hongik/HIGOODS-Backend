package com.higoods.api.config

import org.springframework.test.context.ActiveProfilesResolver

class InfraIntegrateProfileResolver : ActiveProfilesResolver {

    override fun resolve(testClass: Class<*>): Array<String> {
        return arrayOf("infra", "common", "test")
    }
}
