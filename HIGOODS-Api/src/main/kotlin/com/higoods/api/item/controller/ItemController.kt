package com.higoods.api.item.controller

import com.higoods.api.item.dto.request.ItemCreateRequest
import com.higoods.api.item.dto.response.ItemResponse
import com.higoods.api.item.usecase.ItemCreateUseCase
import com.higoods.api.item.usecase.ItemReadUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/project/{project_id}")
class ItemController(
    val itemCreateUseCase: ItemCreateUseCase,
    val itemReadUseCase: ItemReadUseCase
) {

    @PostMapping
    fun create(
        @PathVariable("project_id") projectId: Long,
        @Validated @RequestBody
        itemCreateRequest: ItemCreateRequest
    ): ItemResponse {
        return itemCreateUseCase.execute(projectId, itemCreateRequest)
    }

    @GetMapping("/item/{item_id}")
    fun getItemById(
        @PathVariable("project_id") projectId: Long,
        @PathVariable("item_id") itemId: Long
    ): ItemResponse {
        return itemReadUseCase.findById(itemId)
    }
}
