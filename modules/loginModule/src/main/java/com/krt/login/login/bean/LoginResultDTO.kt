package com.krt.login.login.bean

import com.alibaba.fastjson.annotation.JSONField

data class LoginResultDTO(
        @JSONField(name = "companyId")
        val companyId: Int = -1,
        @JSONField(name = "dealerId")
        val dealerId: String = "",
        @JSONField(name = "dutyName")
        val dutyName: String = "",
        @JSONField(name = "empId")
        val empId: Int = 0,
        @JSONField(name = "empName")
        val empName: String?,
        @JSONField(name = "dutyNameCN")
        val dutyNameCN: String?,
        @JSONField(name = "headPortrait")
        val headPortrait: String = "",
        @JSONField(name = "token")
        val token: String = "",
        @JSONField(name = "userCode")
        val userCode: String = "",
        @JSONField(name = "userName")
        val userName: String = "",
        @JSONField(name = "dutyId")
        val dutyId: Int = -1
)