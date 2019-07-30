package com.krt.home.testdrive.list.bean

import com.alibaba.fastjson.annotation.JSONField

data class TestDriveListEntity(
        @JSONField(name = "appearance")
        val appearance: Int?,
        @JSONField(name = "bookNo")
        val bookNo: String?,
        @JSONField(name = "comfortDegree")
        val comfortDegree: Int?,
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "comprehensive")
        val comprehensive: Int?,
        @JSONField(name = "createdBy")
        val createdBy: Int?,
        @JSONField(name = "createdTime")
        val createdTime: Long = 0,
        @JSONField(name = "customerMobileNo")
        val customerMobileNo: String?,
        @JSONField(name = "customerName")
        val customerName: String?,
        @JSONField(name = "dealerId")
        val dealerId: Int?,
        @JSONField(name = "evaluation")
        val evaluation: String?,
        @JSONField(name = "influenceBuy")
        val influenceBuy: Int?,
        @JSONField(name = "interior")
        val interior: Int?,
        @JSONField(name = "modelPlateNumber")
        val modelPlateNumber: String?,
        @JSONField(name = "passengerRoom")
        val passengerRoom: Int?,
        @JSONField(name = "pcNo")
        val pcNo: String?,
        @JSONField(name = "powerSteering")
        val powerSteering: Int?,
        @JSONField(name = "practicability")
        val practicability: Int?,
        @JSONField(name = "recommend")
        val recommend: Int?,
        @JSONField(name = "reservationType")
        val reservationType: String?,
        @JSONField(name = "safety")
        val safety: Int?,
        @JSONField(name = "service")
        val service: Int?,
        @JSONField(name = "soldBy")
        val soldBy: Int?,
        @JSONField(name = "soldByName")
        val soldByName: String?,
        @JSONField(name = "status")
        var status: String?,
        @JSONField(name = "testDriver")
        val testDriver: String?,
        @JSONField(name = "testDrivingTime")
        val testDrivingTime: Long = 0,
        @JSONField(name = "version")
        val version: Int?
)