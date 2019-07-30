package com.krt.submarine.customerstatuslist.entity

import com.alibaba.fastjson.annotation.JSONField

data class CustomerFollowUpStatusEntity(
        @JSONField(name = "followupList")
        val followupList: List<Followup?>?,
        @JSONField(name = "leadsLevel")
        val leadsLevel: String?,
        @JSONField(name = "nextFoDate")
        val nextFoDate: Long = 0,
        @JSONField(name = "pcName")
        val pcName: String?,
        @JSONField(name = "pcNo")
        val pcNo: String?,
        @JSONField(name = "seriesCode")
        val seriesCode: String?,
        @JSONField(name = "modelCode")
        val modelCode: String?
)