package com.krt.business.update.bean

import com.alibaba.fastjson.annotation.JSONField

data class UpdateEntityDTO(
        @JSONField(name = "versionCode") var versionCode: String = "0.0.0",
        @JSONField(name = "downloadUrl") val downloadUrl: String = "",
        @JSONField(name = "title") val title: String?,
        @JSONField(name = "content") val content: String?,
        @JSONField(name = "appUsable") val appUsable: Boolean = true,
        @JSONField(name = "msg") val msg: String?
)