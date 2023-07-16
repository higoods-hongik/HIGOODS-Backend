package com.higoods.api.test.config

import org.springframework.test.context.ActiveProfilesResolver

class ApiIntegrateProfileResolver : ActiveProfilesResolver {

    override fun resolve(testClass: Class<*>): Array<String> {
        return arrayOf("infra", "domain", "test", "common")
    }
}
