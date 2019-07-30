package com.krt.application.main

import android.app.Application
import com.krt.frame.frame.mvvm.BaseViewModel


class MainViewModel(application: Application) : BaseViewModel(application) {

    fun checkAppVersion() {
//        UpdateAppDialog.checkVersion(this, true)
    }

}


