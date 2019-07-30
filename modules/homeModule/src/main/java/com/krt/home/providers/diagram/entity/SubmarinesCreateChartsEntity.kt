package com.krt.home.providers.diagram.entity

import com.alibaba.fastjson.annotation.JSONField

data class SubmarinesCreateChartsEntity(
        @JSONField(name = "cueCount")
        val cueCount: Int = 0,
        @JSONField(name = "soldBy")
        val soldBy: Int?,
        @JSONField(name = "soldByName")
        val soldByName: String?
)