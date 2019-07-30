package com.krt.component


import com.krt.component.routhpath.*
import com.krt.component.service.*
import com.krt.frame.app.initModule

class ModuleServiceFactory private constructor() {

    //module service start,do not delete this

    var appService: IAppModuleService? = null

    val homeService by lazy { initModule(RouterPathHome.APPLICATION) as? IHomeModuleService }

    val loginService by lazy { initModule(RouterPathLogin.APPLICATION) as? ILoginModuleService }

    val categoryService by lazy { initModule(RouterPathCategory.APPLICATION) as? ICategoryModuleService }

    val clueAllocationModuleService by lazy { initModule(RouterPathClueAllocation.APPLICATION) as? IClueAllocationModuleService }

    val remindService by lazy { initModule(RouterPathRemind.APPLICATION) as? IRemindModuleService }

    val submarineService by lazy { initModule(RouterPathSubmarine.APPLICATION) as? ISubmarineModuleService }

    //module service end,do not delete this

    private object Inner {
        val serviceFactory = ModuleServiceFactory()
    }


    /**
     * 应用初始化创建Application后，可以预先初始化一些lazy模块
     */
    fun initModuleAfterApplicationCreate() {
        homeService
    }

    companion object {

        /**
         * 通过静态内部类方式实现 ModuleServiceFactory 的单例
         */
        val instance: ModuleServiceFactory
            get() = Inner.serviceFactory
    }
}
