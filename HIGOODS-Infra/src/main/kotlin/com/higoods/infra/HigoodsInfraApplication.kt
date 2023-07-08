package com.higoods.infra

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HigoodsInfraApplication

fun main(args: Array<String>) {
    runApplication<HigoodsInfraApplication>(*args)
}
