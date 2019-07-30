package com.krt.application.activity

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.business.update.UpdateAppDialog
import com.krt.component.ModuleServiceFactory
import com.krt.component.service.ILoginModuleService
import com.krt.frame.frame.mvvm.BaseViewModel


class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    val logoutSuccessLiveData = MutableLiveData<Boolean>()

    init {
        UpdateAppDialog.checkVersion(this, true)
    }

    fun logout() {
        ModuleServiceFactory.instance.loginService?.logout(this, object : ILoginModuleService.ILoginOutCallBack {

            override fun onSuccess() {
                logoutSuccessLiveData.value = true
            }

            override fun onError() {
            }
        })
    }
}


