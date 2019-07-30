package com.krt.application

import com.krt.component.service.IAppModuleService

class AppModuleService : IAppModuleService {

    var navigationCallBack: INavigationCallBack? = null

    override fun openNavigationView() {
        navigationCallBack?.open()
    }

    interface INavigationCallBack {
        fun open()
    }
}