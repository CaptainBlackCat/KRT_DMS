package com.krt.home

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathHome
import com.krt.frame.app.BaseApp
import com.krt.frame.app.IModuleService

@Route(path = RouterPathHome.APPLICATION)
class HomeModuleApp : BaseApp() {

    override fun initModuleAppService(): IModuleService {
        return HomeModuleService()
    }

    override fun initData() {
    }

}