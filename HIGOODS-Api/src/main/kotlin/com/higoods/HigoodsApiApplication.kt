package com.higoods

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HigoodsApiApplication

fun main(args: Array<String>) {
    runApplication<HigoodsApiApplication>(*args)
}
