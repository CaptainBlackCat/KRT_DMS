package com.krt.business.config.information

import android.annotation.SuppressLint
import com.alibaba.fastjson.JSONObject
import com.krt.base.ext.toParams
import com.krt.business.config.information.bean.InformationConfig
import com.krt.business.config.information.bean.InformationConfigEntity
import com.krt.business.service.NetUrlCommon
import com.krt.business.user.UserDefault
import com.krt.frame.ext.showToast
import com.lzy.okgo.OkGo
import com.lzy.okgo.convert.StringConvert
import com.lzy.okgo.model.Response
import com.lzy.okrx2.adapter.ObservableResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object InformationDataConfig {

    private var informationConfig: InformationConfig? = null

    @SuppressLint("CheckResult")
    fun loadInformationData() {
        //不需要重复加载
        if (null != informationConfig) {
            return
        }

        val params = listOf("companyId", UserDefault.companyId).toParams()

        OkGo.get<String>(NetUrlCommon.INFORMATION_GET)
                .converter(StringConvert())
                .params(params)
                .adapt<Observable<Response<String>>>(ObservableResponse())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val result = JSONObject.parseObject(it.body(), InformationConfigEntity::class.java)
                    informationConfig = result.data
                }, {

                }, {

                })

    }

    fun getInformationConfig(): InformationConfig? {
        return if (informationConfig == null) {
            showToast("正在加载数据，请稍等")
            loadInformationData()
            null
        } else {
            informationConfig
        }
    }

    fun clear() {
        informationConfig = null
    }
}