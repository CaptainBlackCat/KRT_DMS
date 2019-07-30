package com.krt.business.bean

import com.alibaba.fastjson.annotation.JSONField

data class CustomerLevelListEntity(
        @JSONField(name = "cueCount")
        val cueCount: Int = 0,
        @JSONField(name = "dictValue")
        val dictValue: String = "",
        @JSONField(name = "leadsLevel")
        val leadsLevel: Int?
)