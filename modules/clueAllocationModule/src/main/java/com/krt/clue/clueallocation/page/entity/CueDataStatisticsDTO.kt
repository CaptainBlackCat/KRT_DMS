package com.krt.clue.clueallocation.page.entity

import com.alibaba.fastjson.annotation.JSONField

data class CueDataStatisticsDTO(
        @JSONField(name = "customers")
        val customers: Int?,
        @JSONField(name = "leaveOffice")
        val leaveOffice: Int?,
        @JSONField(name = "onDuty")
        val onDuty: Int?,
        @JSONField(name = "unallocated")
        val unallocated: Int?
)