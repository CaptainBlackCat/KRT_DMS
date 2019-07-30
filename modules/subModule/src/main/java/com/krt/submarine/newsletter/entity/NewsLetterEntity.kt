package com.krt.submarine.newsletter.entity

import com.alibaba.fastjson.annotation.JSONField

data class NewsLetterEntity(
        @JSONField(name = "finish")
        val finish: Int = 0,
        @JSONField(name = "foBy")
        val foBy: String,
        @JSONField(name = "incomplete")
        val incomplete: Int = 0,
        @JSONField(name = "need")
        val need: Int = 0
)