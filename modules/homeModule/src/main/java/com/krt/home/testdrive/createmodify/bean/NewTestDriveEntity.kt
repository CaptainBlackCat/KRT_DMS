package com.krt.home.testdrive.createmodify.bean

import com.alibaba.fastjson.annotation.JSONField

data class NewTestDriveEntity(
        @JSONField(name = "bookNo")
        val bookNo: String?,
        @JSONField(name = "soldBy")
        val soldBy: Int?,
        @JSONField(name = "customerMobileNo")
        val customerMobileNo: String?,
        @JSONField(name = "modelPlateNumber")
        val modelPlateNumber: String?,
        @JSONField(name = "createdTime")
        val createdTime: Long?,
        @JSONField(name = "pcNo")
        val pcNo: String?,
        @JSONField(name = "updatedTime")
        val updatedTime: Long?,
        @JSONField(name = "updatedBy")
        val updatedBy: Int?,
        @JSONField(name = "dealerId")
        val dealerId: Int?,
        @JSONField(name = "customerName")
        val customerName: String?,
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "soldByName")
        val soldByName: String?,
        @JSONField(name = "createdBy")
        val createdBy: Int?,
        @JSONField(name = "reservationType")
        val reservationType: String?,
        @JSONField(name = "status")
        val status: String?
)