package com.higoods.api.test.config

import com.higoods.common.HigoodsCommonApplication
import com.higoods.domain.HigoodsDomainApplication
import com.higoods.infra.HigoodsInfraApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [HigoodsCommonApplication::class, HigoodsDomainApplication::class, HigoodsInfraApplication::class])
class ApiIntegrateTestConfig
