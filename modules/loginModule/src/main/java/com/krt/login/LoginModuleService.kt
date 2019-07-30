package com.krt.login

import com.krt.business.user.UserDefault
import com.krt.component.service.ILoginModuleService
import com.krt.frame.frame.lce.LceView
import com.krt.login.service.NetUrlLogin
import com.krt.network.base.LCEParams
import com.krt.network.httpGet

class LoginModuleService : ILoginModuleService {

    override fun logout(lceView: LceView, callBack: ILoginModuleService.ILoginOutCallBack) {
        httpGet<String>(
                NetUrlLogin.USER_LOGIN_OUT,
                lceView,
                defaultTokenHead = true,
                lce = LCEParams(showLoadingDialog = true)
        )
        {
            UserDefault.loginOut()
            callBack.onSuccess()
        }
    }
}