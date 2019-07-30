package com.krt.home.providers.entity

import com.alibaba.fastjson.annotation.JSONField

data class HomeFunctionChooseEntity(
        @JSONField(name = "customerCount")
        val customerCount: Int?,
        @JSONField(name = "modules")
        val modules: List<HomeFunctionChooseModuleEntity>?
)