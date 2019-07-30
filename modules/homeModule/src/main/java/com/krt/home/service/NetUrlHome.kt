package com.krt.home.service

import com.krt.frame.app.config.ConfigKeys
import com.krt.frame.app.config.KRT

object NetUrlHome {

    private val BASE_URL = KRT.getConfiguration<String>(ConfigKeys.API_HOST)

    //    按角色职业配置首页信息
    val GET_HOME_PAGE_CONF_DATA by lazy { "$BASE_URL/api/home/getHomepageConfData" }


    //    条件查询客户试乘试驾列表接口
    val LIST_TRIAL_DRIVE by lazy { "$BASE_URL/api/drive/listTrialDrive" }


    //    查询客户试乘试驾详情
    val GET_TRIAL_DRIVE_INFO by lazy { "$BASE_URL/api/drive/getTrialDriveInfo" }

    //    本月店销潜客建档排行榜
    val FIND_ESTABLISH_CUSTOMER_FILE_RANK by lazy { "$BASE_URL/api/home/findEstablishCustomerFileRank" }

    //    新建试乘试驾
    val SAVE_TRIAL_DRIVE_INFO by lazy { "$BASE_URL/api/drive/saveTrialDriveInfo" }

    //    试驾单修改
    val UPDATE_TRIAL_DRIVE_INFO by lazy { "$BASE_URL/api/drive/updateTrialDriveInfo" }
}