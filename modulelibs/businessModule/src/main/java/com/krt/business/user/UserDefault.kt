package com.krt.business.user


import android.content.Context
import com.krt.base.ext.empty
import com.krt.base.ext.pref
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.db.AppPostShow
import com.krt.business.db.CommonDbManager
import com.krt.business.db.contract.UserContract
import com.krt.frame.app.config.KRT
import java.io.Serializable

object UserDefault : Serializable {

    /**
     * 长时间运行在后台，数据可能丢失，存储在本地缓存，登入时清空
     */
    var companyId: Int by pref("companyId", -1)       //公司id

    var empId: Int by pref("empId", -1)       //用户数据库id，唯一标识

    var dealerId: String by pref("dealerId", "")       //经销商id，一个公司下可能有多个经销商

    var userCode: String by pref("userCode", "")        //用户名，账号

    var token: String  by pref("token", "")          //后台   section  标识

    var headPortrait: String  by pref("headPortrait", "")       //头像地址

    var userName: String  by pref("userName", "")       //系统管理员      用户名   中文

    var dutyName: String  by pref("dutyName", "")      //MANAGER      用户身份     英文名

    var empName: String?  by pref("empName", "")         //用户显示名

    var dutyNameCN: String?  by pref("dutyNameCN", "")      //销售经理(经销商)      用户身份  中文名

    var dutyId: Int by pref("dutyId", -1)        //10260050    后台数据用户身份标识

    /**
     * 清空跟用户有关的所有信息
     */
    fun clear() {
        companyId = Int.empty()
        empId = Int.empty()
        dealerId = String.empty()
        userCode = String.empty()
        token = String.empty()
        headPortrait = String.empty()
        userName = String.empty()
        dutyName = String.empty()
        empName = String.empty()
        dutyNameCN = String.empty()
        dutyId = Int.empty()

        KRT.getApplicationContext().getSharedPreferences(this.javaClass.name, Context.MODE_PRIVATE)
                .edit().clear().apply()
    }

    /**
     *  判断当前用户是否为   销售经理
     */
    fun isUserSalesManager() = UserDefault.dutyId == 10260050

    /**
     * 保存 用户 数据
     */
    fun saveUser(
            companyId: Int,
            empId: Int,
            dealerId: String,
            userCode: String,
            token: String,
            headPortrait: String,
            userName: String,
            dutyName: String,
            empName: String?,
            dutyNameCN: String?,
            dutyId: Int
    ): Boolean {
        val result = CommonDbManager.saveUser(
                companyId.toString(), empId.toString(), dealerId, userCode, token, headPortrait,
                userName, dutyName, empName, dutyNameCN, dutyId
        )

        if (result) {
            UserDefault.companyId = companyId
            UserDefault.empId = empId
            UserDefault.dealerId = dealerId
            UserDefault.userCode = userCode
            UserDefault.token = token
            UserDefault.headPortrait = headPortrait
            UserDefault.userName = userName
            UserDefault.dutyName = dutyName
            UserDefault.empName = empName
            UserDefault.dutyNameCN = dutyNameCN
            UserDefault.dutyId = dutyId
        }

        //登入成功后要获取配置信息
        InformationDataConfig.loadInformationData()
        return result
    }

    /**
     * 从数据库中获取  用户  信息，并缓存
     */
    fun readUserFromDataBase(): Boolean {
        val result = CommonDbManager.getUser()

        result[UserContract.COLUMN_NAME_COMPANY_ID]?.let {
            UserDefault.companyId = it.toInt()
        }

        result[UserContract.COLUMN_NAME_EMP_ID]?.let {
            UserDefault.empId = it.toInt()
        }
        result[UserContract.COLUMN_NAME_DEALER_ID]?.let {
            UserDefault.dealerId = it
        }

        result[UserContract.COLUMN_NAME_USER_CODE]?.let {
            UserDefault.userCode = it
        }
        result[UserContract.COLUMN_NAME_TOKEN]?.let {
            UserDefault.token = it
        }
        result[UserContract.COLUMN_NAME_HEAD_PORTRAIT]?.let {
            UserDefault.headPortrait = it
        }
        result[UserContract.COLUMN_NAME_USERNAME]?.let {
            UserDefault.userName = it
        }

        result[UserContract.COLUMN_NAME_DUTY_NAME]?.let {
            UserDefault.dutyName = it
        }

        result[UserContract.COLUMN_NAME_EMP_NAME]?.let {
            UserDefault.empName = it
        }

        result[UserContract.COLUMN_NAME_DUTY_NAME_CN]?.let {
            UserDefault.dutyNameCN = it
        }

        result[UserContract.COLUMN_NAME_DUTY_ID]?.let {
            UserDefault.dutyId = it.toInt()
        }

        //从数据库里读取缓存信息，如果有缓存信息，则读取配置信息
        if (result.isNotEmpty()) {
            InformationDataConfig.loadInformationData()
        }

        return result.isNotEmpty()
    }

    /**
     * 退出登录
     */
    fun loginOut() {
        //清除缓存
        clear()
        //删除数据库用户数据
        CommonDbManager.clearUser()
        //删除目前存储的岗位选择信息
        AppPostShow.clear()
        //登出后要清除配置信息
        InformationDataConfig.clear()
    }

}
