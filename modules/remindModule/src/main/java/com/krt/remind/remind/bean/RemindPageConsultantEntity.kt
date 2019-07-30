package com.krt.remind.remind.bean

import com.alibaba.fastjson.annotation.JSONField

data class RemindPageConsultantEntity(
        @JSONField(name = "historicalAppointment")
        val historicalAppointment: Int?,
        @JSONField(name = "historicalUnfinished")
        val historicalUnfinished: Int?,
        @JSONField(name = "overtimeCount")
        val overtimeCount: Int? = 0,
        @JSONField(name = "thisMonthAppointment")
        val thisMonthAppointment: Int?,
        @JSONField(name = "thisMonthUnfinished")
        val thisMonthUnfinished: Int?,
        @JSONField(name = "withinTwoDaysCount")
        val withinTwoDaysCount: Int?
)