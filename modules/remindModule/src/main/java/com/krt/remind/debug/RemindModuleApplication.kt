package com.krt.remind.debug

import com.krt.business.global.BaseApplication
import com.krt.business.user.UserDefault

@Deprecated("")
class RemindModuleApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        UserDefault.companyId = 1001
        UserDefault.dealerId = "0"
    }

}