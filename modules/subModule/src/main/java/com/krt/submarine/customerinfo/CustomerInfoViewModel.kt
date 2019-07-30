package com.krt.submarine.customerinfo

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet
import com.krt.submarine.customerinfo.bean.CustomerInfoEntity
import com.krt.submarine.service.NetUrlSubmarine

class CustomerInfoViewModel(application: Application) : BaseViewModel(application) {

    val listDataLiveData = MutableLiveData<CustomerInfoEntity>()

    fun loadFirstComingData(pcNo: String) {
        val params = listOf(
                "companyId", UserDefault.companyId, "empId", UserDefault.empId,
                "pcNo", pcNo
        ).toParams()

        httpGet<CustomerInfoEntity>(NetUrlSubmarine.FOLLOW_UP_QUERY, this, httpParams = params).toObject {
            listDataLiveData.value = it
        }
    }

}