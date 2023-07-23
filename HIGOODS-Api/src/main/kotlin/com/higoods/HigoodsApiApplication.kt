package com.higoods

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
class HigoodsApiApplication

fun main(args: Array<String>) {
    runApplication<HigoodsApiApplication>(*args)
}
