package com.krt.component.service

import com.krt.frame.app.IModuleService
import com.krt.frame.frame.lce.LceView

/**
 * Created by xbf on 2018/8/7
 */
interface ILoginModuleService : IModuleService {

    fun logout(lceView: LceView, callBack: ILoginOutCallBack)

    interface ILoginOutCallBack {

        fun onSuccess()

        fun onError()
    }

}
