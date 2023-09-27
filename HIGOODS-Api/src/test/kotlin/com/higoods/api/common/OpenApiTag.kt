package com.higoods.api.common

enum class OpenApiTag(
    val value: String
) {
    TEST("test"),
    AUTH("Auth"),
    USER("User"),
    PROJECT("PROJECT"),
    ITEM("ITEM"),
    ORDER_FORM("ORDER_FORM"),
    DISTRIBUTION("[어드민] 배부")
}
