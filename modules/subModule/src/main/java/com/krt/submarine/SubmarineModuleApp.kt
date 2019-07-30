package com.krt.submarine

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathSubmarine
import com.krt.frame.app.BaseApp
import com.krt.frame.app.IModuleService

@Route(path = RouterPathSubmarine.APPLICATION)
class SubmarineModuleApp : BaseApp() {

    override fun initModuleAppService(): IModuleService {
        return SubmarineModuleService()
    }

    override fun initData() {
    }

}