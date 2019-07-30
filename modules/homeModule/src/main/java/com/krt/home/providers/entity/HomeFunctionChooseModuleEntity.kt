package com.krt.home.providers.entity

import com.alibaba.fastjson.annotation.JSONField

data class HomeFunctionChooseModuleEntity(
        @JSONField(name = "androidRoute")
        val androidRoute: String?,
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "dealerId")
        val dealerId: Int?,
        @JSONField(name = "moduleName")
        val moduleName: String?,
        @JSONField(name = "moduleNo")
        val moduleNo: Int?,
        @JSONField(name = "seq")
        val seq: Int?,
        @JSONField(name = "switchEnd")
        val switchEnd: String?,
        @JSONField(name = "moduleIcon")
        val moduleIcon: String?
)