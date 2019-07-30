package com.krt.submarine.defeataudit.detail.entity

import com.alibaba.fastjson.annotation.JSONField


data class DefeatAuditDetailEntity(
        @JSONField(name = "foBy")
        val foBy: String?,
        @JSONField(name = "pcNo")
        val pcNo: String?,
        @JSONField(name = "soldByName")
        val soldByName: String?,
        @JSONField(name = "seriesCode")
        val seriesCode: String?,
        @JSONField(name = "modelCode")
        val modelCode: String?,
        @JSONField(name = "defeatReason")
        val defeatReason: String?,
        @JSONField(name = "soldBy")
        val soldBy: Int?,
        @JSONField(name = "historyTrack")
        val historyTrack: List<HistoryTrack?>?,
        @JSONField(name = "pcName")
        val pcName: String?,
        @JSONField(name = "pcMobile")
        val pcMobile: String?,
        @JSONField(name = "foNo")
        val foNo: String?,
        @JSONField(name = "summarizeAnalyze")
        val summarizeAnalyze: String?
)

data class HistoryTrack(
        @JSONField(name = "foBy")
        val foBy: String?,
        @JSONField(name = "foDate")
        val foDate: Long?,
        @JSONField(name = "foProcesse")
        val foProcesse: String?
)