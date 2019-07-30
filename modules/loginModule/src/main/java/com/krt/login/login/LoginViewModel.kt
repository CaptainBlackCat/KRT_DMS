package com.krt.login.login

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.krt.base.ext.toJson
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.login.login.bean.LoginResultDTO
import com.krt.login.service.NetUrlLogin
import com.krt.network.base.LCEParams
import com.krt.network.httpPost

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val loginSuccessLiveData = MutableLiveData<Boolean>()

    init {
//        UpdateAppDialog.checkVersion(this, true)
    }

    fun login(companyId: String, userCode: String, password: String) {
        val json = listOf("companyId", companyId, "userCode", userCode, "password", password).toJson()
        httpPost<LoginResultDTO>(NetUrlLogin.USER_LOGIN, this, upJson = json, lce = LCEParams(showLoadingDialog = true)).toObject {
            loginSuccessLiveData.value = true
            UserDefault.saveUser(
                    it!!.companyId,
                    it.empId,
                    it.dealerId,
                    it.userCode,
                    it.token,
                    it.headPortrait,
                    it.userName,
                    it.dutyName,
                    it.empName,
                    it.dutyNameCN,
                    it.dutyId
            )
            SPUtils.getInstance().put(COMPANY_CODE, companyId)
            SPUtils.getInstance().put(USER_CODE, userCode)
        }
    }

    companion object {

        const val COMPANY_CODE = "company_code"

        const val USER_CODE = "user_code"

    }
}