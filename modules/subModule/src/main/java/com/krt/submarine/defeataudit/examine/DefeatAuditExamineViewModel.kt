package com.krt.submarine.defeataudit.examine

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toJson
import com.krt.base.ext.toParams
import com.krt.business.bean.SalesConsultantEntity
import com.krt.business.service.NetUrlCommon
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.base.LCEParams
import com.krt.network.httpGet
import com.krt.network.httpPost
import com.krt.submarine.service.NetUrlSubmarine

class DefeatAuditExamineViewModel(application: Application) : BaseViewModel(application) {

    val upSuccessLiveData = MutableLiveData<Boolean>()

    val customerListLiveData = MutableLiveData<List<SalesConsultantEntity>>()

    fun upData(defeatApplyStatus: String, foNo: String, remark: String, soldBy: String? = null) {
        val upJson = listOf(
                "defeatApplyStatus", defeatApplyStatus,
                "foNo", foNo,
                "remark", remark,
                "soldBy", soldBy
        ).toJson()

        httpPost<String>(
                NetUrlSubmarine.AUDIT_DEFEAT_APPLICATION,
                this,
                upJson = upJson,
                lce = LCEParams(showLoadingDialog = true)
        )
        {
            upSuccessLiveData.value = true
        }
    }

    fun showSalesConsultantList() {
        if (customerListLiveData.value != null) {
            customerListLiveData.value = customerListLiveData.value
            return
        }

        val params = listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId).toParams()

        httpGet<SalesConsultantEntity>(
                NetUrlCommon.LIST_SALES_CONSULTANT,
                this,
                httpParams = params,
                lce = LCEParams(notStartLce = true)
        ).toList {
            customerListLiveData.value = it
        }
    }

}