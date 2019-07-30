package com.krt.category

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathCategory
import com.krt.frame.app.BaseApp
import com.krt.frame.app.IModuleService

@Route(path = RouterPathCategory.APPLICATION)
class CategoryModuleApp : BaseApp() {

    override fun initModuleAppService(): IModuleService {
        return CategoryModuleService()
    }

    override fun initData() {
    }

}