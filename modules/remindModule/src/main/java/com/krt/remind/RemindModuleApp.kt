package com.krt.remind

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathRemind
import com.krt.frame.app.BaseApp
import com.krt.frame.app.IModuleService

@Route(path = RouterPathRemind.APPLICATION)
class RemindModuleApp : BaseApp() {

    override fun initModuleAppService(): IModuleService {
        return RemindModuleService()
    }

    override fun initData() {
    }

}