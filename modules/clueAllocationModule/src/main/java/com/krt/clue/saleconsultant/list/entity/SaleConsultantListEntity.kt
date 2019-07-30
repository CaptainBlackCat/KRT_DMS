package com.krt.clue.saleconsultant.list.entity

import com.alibaba.fastjson.annotation.JSONField

data class SaleConsultantListEntity(
        @JSONField(name = "soldByName")
        val soldByName: String?,
        @JSONField(name = "soldBy")
        val soldBy: Int?,
        @JSONField(name = "cueCount")
        val cueCount: Int?
)