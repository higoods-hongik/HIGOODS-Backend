package com.higoods.infra.config.feign.feign

import com.higoods.infra.api.BaseFeignClientPackage
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import feign.Logger

@Configuration
@EnableFeignClients(basePackageClasses = [BaseFeignClientPackage::class])
class FeignCommonConfig {

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }
}
