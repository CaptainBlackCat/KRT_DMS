package com.krt.login.service

import com.krt.frame.app.config.ConfigKeys
import com.krt.frame.app.config.KRT

object NetUrlLogin {

    private val BASE_URL = KRT.getConfiguration<String>(ConfigKeys.API_HOST)

    val USER_LOGIN by lazy {
        "$BASE_URL/user/login"
    }

    val USER_LOGIN_OUT by lazy { "$BASE_URL/user/logout" }

}