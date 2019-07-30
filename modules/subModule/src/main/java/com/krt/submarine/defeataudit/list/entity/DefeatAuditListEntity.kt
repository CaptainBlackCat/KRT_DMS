package com.krt.submarine.defeataudit.list.entity

import com.alibaba.fastjson.annotation.JSONField

data class DefeatAuditListEntity(
        @JSONField(name = "foBy")
        val foBy: String?,
        @JSONField(name = "passCount")
        val passCount: Int?,
        @JSONField(name = "unauditedCount")
        val unauditedCount: Int?
)