package com.krt.business.bean

import com.alibaba.fastjson.annotation.JSONField

/**
 * 销售顾问
 */
data class SalesConsultantEntity(
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "createdBy")
        val createdBy: Int?,
        @JSONField(name = "createdTime")
        val createdTime: Long?,
        @JSONField(name = "dutyId")
        val dutyId: Int?,
        @JSONField(name = "dutyName")
        val dutyName: String?,
        @JSONField(name = "empId")
        val empId: Int?,
        @JSONField(name = "empName")
        val empName: String?,
        @JSONField(name = "groupCode")
        val groupCode: String?,
        @JSONField(name = "headPortrait")
        val headPortrait: String?,
        @JSONField(name = "isInactive")
        val inactive: Int?,
        @JSONField(name = "isOnDuty")
        val onDuty: Int?,
        @JSONField(name = "isOnline")
        val online: Int?,
        @JSONField(name = "lastLoginTime")
        val lastLoginTime: Long?,
        @JSONField(name = "loginTimes")
        val loginTimes: Int?,
        @JSONField(name = "orgId")
        val orgId: Int?,
        @JSONField(name = "password")
        val password: String?,
        @JSONField(name = "pwdUpdatedDate")
        val pwdUpdatedDate: Long?,
        @JSONField(name = "updatedBy")
        val updatedBy: Int?,
        @JSONField(name = "updatedTime")
        val updatedTime: Long?,
        @JSONField(name = "userCategory")
        val userCategory: Int?,
        @JSONField(name = "userCode")
        val userCode: String?,
        @JSONField(name = "userId")
        val userId: Int?,
        @JSONField(name = "userName")
        val userName: String?,
        @JSONField(name = "version")
        val version: Int?
)