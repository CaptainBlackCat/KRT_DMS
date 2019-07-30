package com.krt.home.testdrive.start

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toJson
import com.krt.base.ext.toParams
import com.krt.business.bean.SalesConsultantEntity
import com.krt.business.service.NetUrlCommon
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.home.service.NetUrlHome
import com.krt.network.base.LCEParams
import com.krt.network.base.UpFileParams
import com.krt.network.httpGet
import com.krt.network.httpPost
import java.io.File

class StartTestDriveViewModel(application: Application) : BaseViewModel(application) {

    val customerListLiveData = MutableLiveData<List<SalesConsultantEntity>>()

    val saveSuccessLiveData = MutableLiveData<Boolean>()

    fun showCustomerList() {
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

    fun upLoadFile(upLoadImageFile: File, actionNetFilePath: (String) -> Unit) {
        httpPost<String>(
                NetUrlCommon.FILE_UPLOAD,
                this,
                upFile = UpFileParams(upFile = upLoadImageFile),
                lce = LCEParams(showLoadingDialog = true)
        ) {
            actionNetFilePath.invoke(it)
        }
    }

    fun save(
            bookNo: String,
            status: String,
            testDrive: String,
            photoDlOriginal: String,
            photoDlCopy: String,
            dlIss: Long,
            dlExp: Long,
            photoIdCardFront: String,
            photoIdCardBack: String,
            testDriver: String,
            idCardNo: String,
            customerMobileNo: String,
            customerSex: String,
            customerBirthday: String,
            customerAddress: String,
            photoAgreement: String
    ) {
        val upJson = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "bookNo", bookNo,
                "isCancel", 0,
                "status", status,
                "testDrive", testDrive,
                "photoDlOriginal", photoDlOriginal,
                "photoDlCopy", photoDlCopy,
                "dlIss", dlIss,
                "dlExp", dlExp,
                "photoIdCardFront", photoIdCardFront,
                "photoIdCardBack", photoIdCardBack,
                "testDriver", testDriver,
                "idCardNo", idCardNo,
                "customerMobileNo", customerMobileNo,
                "customerSex", customerSex,
                "customerBirthday", customerBirthday,
                "customerAddress", customerAddress,
                "photoAgreement", photoAgreement
        ).toJson()

        httpPost<String>(
                NetUrlHome.UPDATE_TRIAL_DRIVE_INFO, this, upJson = upJson,
                lce = LCEParams(showLoadingDialog = true)
        ) {
            saveSuccessLiveData.value = true
        }
    }

}