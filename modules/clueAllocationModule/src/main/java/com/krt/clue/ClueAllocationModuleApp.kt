package com.krt.clue

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathClueAllocation
import com.krt.frame.app.BaseApp
import com.krt.frame.app.IModuleService

@Route(path = RouterPathClueAllocation.APPLICATION)
class ClueAllocationModuleApp : BaseApp() {

    override fun initModuleAppService(): IModuleService {
        return ClueAllocationModuleService()
    }

    override fun initData() {
    }

}