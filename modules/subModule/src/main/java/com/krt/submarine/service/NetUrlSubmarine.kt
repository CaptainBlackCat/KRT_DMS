package com.krt.submarine.service

import com.krt.frame.app.config.ConfigKeys
import com.krt.frame.app.config.KRT

object NetUrlSubmarine {

    private val BASE_URL = KRT.getConfiguration<String>(ConfigKeys.API_HOST)

    //查询今日顾问跟进状态信息数据
    val LIST_TODAY_FOLLOW_UP_DATA by lazy { "$BASE_URL/api/briefing/listTodayFollowupData" }

    //查询今日各跟进状态顾问跟进客户信息:::  应跟进、已完成、未完成
    val CUSTOMER_FOLLOW_UP_STATUS_LIST by lazy { "$BASE_URL/api/briefing/listFollowupCustomerByStatus" }

    //顾问战败信息统计
    val DEFEATED_FOR_REVIEW_LIST by lazy { "$BASE_URL/api/defeat/listConsultantDefeatData" }

    //查询战败客户申请列表
    val DEFEATED_FOR_REVIEW_DETAIL by lazy { "$BASE_URL/api/defeat/listConsultantDefeatApplication" }

    //客户手机号码检查     ::检查客户手机号码在系统中是否存在
    val RECEPTION_FILING_CHECK by lazy { "$BASE_URL/api/ReceptionFiling/check" }

    //新建潜客档案
    val RECEPTION_FILING_SAVE by lazy { "$BASE_URL/api/ReceptionFiling/save" }

    //潜客跟进分类查询
    val FOLLOW_UP_LIST by lazy { "$BASE_URL/api/followup/list" }

    //新建跟进记录       ::用户注册接口
    val FOLLOW_UP_SAVE by lazy { "$BASE_URL/api/followup/save" }

    //潜客跟进详情
    val FOLLOW_UP_QUERY by lazy { "$BASE_URL/api/followup/query" }

    //潜客历史记录
    val FOLLOW_UP_HISTORY_RECORD by lazy { "$BASE_URL/api/followup/recordingList" }

    //审核战败申请
    val AUDIT_DEFEAT_APPLICATION by lazy { "$BASE_URL/api/defeat/auditDefeatApplication" }
}