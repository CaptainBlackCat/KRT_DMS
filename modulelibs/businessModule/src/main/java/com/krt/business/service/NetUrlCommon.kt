package com.krt.business.service

import com.krt.frame.app.config.ConfigKeys
import com.krt.frame.app.config.KRT

object NetUrlCommon {

    private val BASE_URL = KRT.getConfiguration<String>(ConfigKeys.API_HOST)

    //档案选项数据
    val INFORMATION_GET by lazy { "$BASE_URL/api/information/get" }

    //查询 销售顾问信息列表
    val LIST_SALES_CONSULTANT by lazy { "$BASE_URL/api/customer/listConsultant" }

    //各等级客户统计
    val FIND_CUSTOMER_COUNT_BY_LEVEL_GROUP by lazy { "$BASE_URL/api/customer/findCustomerCountByLevelGroup" }

    //文件上传接口
    val FILE_UPLOAD by lazy { "$BASE_URL/file/upload" }
}