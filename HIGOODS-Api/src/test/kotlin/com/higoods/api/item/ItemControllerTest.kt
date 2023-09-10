package com.higoods.api.item

import com.higoods.api.common.ARRAY
import com.higoods.api.common.BaseControllerTest
import com.higoods.api.common.ENUM
import com.higoods.api.common.NUMBER
import com.higoods.api.common.OpenApiTag
import com.higoods.api.common.STRING
import com.higoods.api.item.controller.ItemController
import com.higoods.api.item.dto.request.ItemCreateRequest
import com.higoods.api.item.dto.request.ItemOptionGroupRequest
import com.higoods.api.item.dto.request.ItemOptionRequest
import com.higoods.api.item.dto.response.ItemOptionGroupResponse
import com.higoods.api.item.dto.response.ItemOptionResponse
import com.higoods.api.item.dto.response.ItemResponse
import com.higoods.api.item.usecase.ItemCreateUseCase
import com.higoods.api.item.usecase.ItemReadUseCase
import com.higoods.domain.item.domain.ProductCategory
import io.mockk.every
import io.mockk.mockk

class ItemControllerTest : BaseControllerTest() {
    private val itemCreateUseCase: ItemCreateUseCase = mockk()
    private val itemReadUseCase: ItemReadUseCase = mockk()

    override val controller: ItemController = ItemController(
        itemCreateUseCase = itemCreateUseCase,
        itemReadUseCase = itemReadUseCase
    )

    init {
        test("POST /v1/project/{project_id}/items") {

            every { itemCreateUseCase.execute(any(), any()) } returns itemResponse()

            val request = ItemCreateRequest(
                category = ProductCategory.CLOTHES,
                name = "와우양말",
                itemOptionGroups = listOf(
                    ItemOptionGroupRequest(
                        name = "아이템옵션그룹이름 : ex 포장방법선택",
                        itemOptions = listOf(
                            ItemOptionRequest(
                                name = "아이템옵션 : ex 미포장",
                                price = 1000
                            ),
                            ItemOptionRequest(
                                name = "아이템옵션 : ex 짱짱포창",
                                price = 10000
                            )
                        )
                    )
                )
            )

            post("/v1/project/{project_id}/items", pathParams = arrayOf(1), request = request) {
                authorizationHeader(1)
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "아이템 생성",
                        tag = OpenApiTag.ITEM,
                        description = "프로젝트생성 두번째 화면에 요청합니다."
                    ),
                    pathParameters("project_id" means "프로젝트 아이디"),
                    requestFields(
                        "name" type STRING means "제품 이름",
                        "category" type ENUM(ProductCategory::class) means "CLOTHES, OFFICE_SUPPLIES, STUFF, ETC",
                        "itemOptionGroups" type ARRAY means "아이템 옵션의 그룹 리스트",
                        "itemOptionGroups[].name" type STRING means "아이템 옵션의 그룹의 이름 ex 포장방법선택",
                        "itemOptionGroups[].itemOptions" type ARRAY means "아이템 옵션 리스트",
                        "itemOptionGroups[].itemOptions[].name" type STRING means "아이템 옵션이름 ex 미포장",
                        "itemOptionGroups[].itemOptions[].price" type NUMBER means "옵션가액"
                    ),
                    responseFields(
                        "projectId" type NUMBER means "프로젝트 아이디",
                        "id" type NUMBER means "아이템 아이디",
                        "name" type STRING means "제품 이름",
                        "category" type ENUM(ProductCategory::class) means "CLOTHES, OFFICE_SUPPLIES, STUFF, ETC",
                        "itemOptionGroups" type ARRAY means "아이템 옵션의 그룹 리스트",
                        "itemOptionGroups[].name" type STRING means "아이템 옵션의 그룹의 이름 ex 포장방법선택",
                        "itemOptionGroups[].id" type NUMBER means "아이템 아이디",
                        "itemOptionGroups[].itemOptions" type ARRAY means "아이템 옵션 리스트",
                        "itemOptionGroups[].itemOptions[].name" type STRING means "아이템 옵션이름 ex 미포장",
                        "itemOptionGroups[].itemOptions[].price" type NUMBER means "옵션가액",
                        "itemOptionGroups[].itemOptions[].id" type NUMBER means "아이템 아이디"
                    )
                )
        }

        test("GET /v1/project/{project_id}/items/{item_id}") {

            every { itemReadUseCase.findById(any()) } returns itemResponse()

            get("/v1/project/{project_id}/items/{item_id}", pathParams = arrayOf(1, 3)) {
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "아이템 조회",
                        tag = OpenApiTag.ITEM,
                        description = "프로젝트의 아이템 조회 시 요청"
                    ),
                    pathParameters(
                        "project_id" means "프로젝트 아이디",
                        "item_id" means "아이템 아이디"
                    ),
                    responseFields(
                        "projectId" type NUMBER means "프로젝트 아이디",
                        "id" type NUMBER means "아이템 아이디",
                        "name" type STRING means "제품 이름",
                        "category" type ENUM(ProductCategory::class) means "CLOTHES, OFFICE_SUPPLIES, STUFF, ETC",
                        "itemOptionGroups" type ARRAY means "아이템 옵션의 그룹 리스트",
                        "itemOptionGroups[].name" type STRING means "아이템 옵션의 그룹의 이름 ex 포장방법선택",
                        "itemOptionGroups[].id" type NUMBER means "아이템 아이디",
                        "itemOptionGroups[].itemOptions" type ARRAY means "아이템 옵션 리스트",
                        "itemOptionGroups[].itemOptions[].name" type STRING means "아이템 옵션이름 ex 미포장",
                        "itemOptionGroups[].itemOptions[].price" type NUMBER means "옵션가액",
                        "itemOptionGroups[].itemOptions[].id" type NUMBER means "아이템 아이디"
                    )
                )
        }
    }

    private fun itemResponse(): ItemResponse {
        return ItemResponse(
            projectId = 1,
            category = ProductCategory.CLOTHES,
            name = "와우양말",
            id = 100,
            itemOptionGroups = listOf(
                ItemOptionGroupResponse(
                    name = "아이템옵션그룹이름 : ex 포장방법선택",
                    id = 1,
                    itemOptions = listOf(
                        ItemOptionResponse(
                            name = "아이템옵션 : ex 미포장",
                            price = 1000,
                            id = 1
                        ),
                        ItemOptionResponse(
                            name = "아이템옵션 : ex 짱짱포창",
                            price = 10000,
                            id = 3
                        )
                    )
                )
            )
        )
    }
}
