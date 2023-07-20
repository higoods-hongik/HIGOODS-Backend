package com.higoods.api

import com.higoods.api.config.InfraIntegrateSpringBootTest
import io.kotest.core.spec.style.StringSpec

@InfraIntegrateSpringBootTest
class HigoodsApiApplicationTest : StringSpec({
    "contextLoads" {}
})
