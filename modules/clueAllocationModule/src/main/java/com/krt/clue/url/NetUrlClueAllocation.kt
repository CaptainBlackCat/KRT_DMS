package com.krt.clue.url

import com.krt.frame.app.config.ConfigKeys
import com.krt.frame.app.config.KRT

object NetUrlClueAllocation {

    private val BASE_URL = KRT.getConfiguration<String>(ConfigKeys.API_HOST)

    //线索与顾问的统计
    val CUE_DATA_STATISTICS by lazy { "$BASE_URL/api/customer/cueDataStatistics" }

    //某一等级客户列表
    val LIST_CUSTOMER_BY_LEVEL by lazy { "$BASE_URL/api/customer/listCustomerByLevel" }

    //在职、离职销售顾问客户列表
    val LIST_CONSULTANT_CUSTOMER by lazy { "$BASE_URL/api/customer/listConsultantCustomer" }

    //按手机号、名称搜索客户
    val LIST_CUSTOMER_INFO_BY_CRITERIA by lazy { "$BASE_URL/api/customer/listCustomerInfoByCriteria" }

    //潜客线索分配
    val ASSIGN_CLUES_TO_CONSULTANT by lazy { "$BASE_URL/api/customer/assignCluesToConsultant" }

    //某一顾问的潜客列表
    val FIND_CUE_LIST_BY_SOLD by lazy { "$BASE_URL/api/customer/findCueListBySold" }

    //潜客待分配列表
    val LIST_UNALLOCATED_CUE by lazy { "$BASE_URL/api/customer/listUnallocatedCue" }

}