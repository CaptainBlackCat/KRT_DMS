package com.krt.remind.service

import com.krt.frame.app.config.ConfigKeys
import com.krt.frame.app.config.KRT

object NetUrlRemind {

    private val BASE_URL = KRT.getConfiguration<String>(ConfigKeys.API_HOST)

    //顾问端消息提醒
    val FIND_CONSULTANT_INFO by lazy { "$BASE_URL/api/reminder/findConsultantInfo" }

    //经理端消息提醒
    val FIND_MANAGER_INFO by lazy { "$BASE_URL/api/reminder/findMessageInfo" }


}