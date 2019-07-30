package com.krt.submarine.record.history

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet
import com.krt.submarine.customerinfo.bean.Followup
import com.krt.submarine.service.NetUrlSubmarine

class HistoryRecordViewModel(application: Application) : BaseViewModel(application) {

    val listDataLiveData = MutableLiveData<List<Followup>>()

    fun loadData(phone: String) {
        val params = listOf(
                "mobile", phone
        ).toParams()

        httpGet<Followup>(NetUrlSubmarine.FOLLOW_UP_HISTORY_RECORD, this, httpParams = params)
                .toList {
                    listDataLiveData.value = it
                }
    }

}