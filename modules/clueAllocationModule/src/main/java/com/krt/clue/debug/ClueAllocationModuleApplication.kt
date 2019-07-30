package com.krt.clue.debug

import com.krt.business.global.BaseApplication
import com.krt.business.user.UserDefault

@Deprecated("")
class ClueAllocationModuleApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        UserDefault.companyId = 1001
        UserDefault.dealerId = "0"
    }

}