package com.krt.submarine.debug

import com.krt.business.global.BaseApplication
import com.krt.business.user.UserDefault

@Deprecated("潜客跟进")
class SubmarineModuleApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        UserDefault.companyId = 1001
        UserDefault.dealerId = "0"
    }

}