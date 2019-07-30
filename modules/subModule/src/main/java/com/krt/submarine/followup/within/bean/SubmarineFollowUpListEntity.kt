package com.krt.submarine.followup.within.bean

import com.alibaba.fastjson.annotation.JSONField

data class SubmarineFollowUpListEntity(
        @JSONField(name = "colorCode")
        val colorCode: String? = "",
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "foProcesse")
        val foProcesse: String?,
        @JSONField(name = "leadsLevel")
        val leadsLevel: Int? = 0,
        @JSONField(name = "modelCode")
        val modelCode: String? = "",
        @JSONField(name = "modelName")
        val modelName: String? = "",
        @JSONField(name = "nextFoDate")
        val nextFoDate: Long? = 0,
        @JSONField(name = "pcMobile")
        val pcMobile: String? = "",
        @JSONField(name = "pcName")
        val pcName: String? = "",
        @JSONField(name = "pcNo")
        val pcNo: String = "",
        @JSONField(name = "seriesCode")
        val seriesCode: String? = ""
)