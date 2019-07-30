package com.krt.remind.remind.bean

import com.alibaba.fastjson.annotation.JSONField

data class RemindPageManagerEntity(
        @JSONField(name = "defeatCount")
        val defeatCount: Int?,
        @JSONField(name = "noFollowGroup")
        val noFollowGroup: List<NoFollowGroup>?,
        @JSONField(name = "overtimeCount")
        val overtimeCount: Int?,
        @JSONField(name = "unallocated")
        val unallocated: Int?
)