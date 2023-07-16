package com.higoods.api.config

import com.higoods.domain.HigoodsDomainApplication
import com.higoods.infra.HigoodsInfraApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [HigoodsInfraApplication::class, HigoodsDomainApplication::class])
class DomainIntegrateTestConfig
