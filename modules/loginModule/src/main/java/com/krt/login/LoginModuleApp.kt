package com.krt.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathLogin
import com.krt.frame.app.BaseApp
import com.krt.frame.app.IModuleService

@Route(path = RouterPathLogin.APPLICATION)
class LoginModuleApp : BaseApp() {

    override fun initModuleAppService(): IModuleService {
        return LoginModuleService()
    }

    override fun initData() {
    }

}