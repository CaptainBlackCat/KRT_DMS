package com.krt.clue.clueallocation.search.entity

import com.alibaba.fastjson.annotation.JSONField

data class ClueAllocationSearchEntity(
        @JSONField(name = "infoSource")
        val infoSource: String?,
        @JSONField(name = "pcMobile")
        val pcMobile: String?,
        @JSONField(name = "pcName")
        val pcName: String?,
        @JSONField(name = "pcNo")
        val pcNo: String?,
        @JSONField(name = "pcStatus")
        val pcStatus: String?,
        @JSONField(name = "soldBy")
        val soldBy: Int?,
        @JSONField(name = "soldByName")
        val soldByName: String?
)