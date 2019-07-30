package com.krt.submarine.customerstatuslist.entity

import com.alibaba.fastjson.annotation.JSONField

data class Followup(
        @JSONField(name = "foBy")
        val foBy: String?,
        @JSONField(name = "foDate")
        val foDate: Long = 0,
        @JSONField(name = "foProcesse")
        val foProcesse: String?
)