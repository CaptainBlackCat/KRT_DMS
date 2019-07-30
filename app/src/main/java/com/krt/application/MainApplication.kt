package com.krt.application

import com.krt.business.global.BaseApplication
import com.krt.component.ModuleServiceFactory

class MainApplication : BaseApplication() {

    val appModuleService = AppModuleService()

    override fun onCreate() {
        super.onCreate()
        ModuleServiceFactory.instance.appService = appModuleService
        //todo
//        KRT.getConfigurators().withCheckAppUpdateUrl(BuildConfig.CHECK_APP_UPDATE)
    }

}