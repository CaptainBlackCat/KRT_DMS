package com.krt.base.picker.addressbean

import com.alibaba.fastjson.annotation.JSONField

data class City(
        @JSONField(name = "area")
        val area: List<String?>?,
        @JSONField(name = "name")
        val name: String?
)