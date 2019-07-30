package com.krt.component.routhpath

//潜客
interface RouterPathSubmarine {
    companion object {
        const val TEST_ACTIVITY = "/submarine/test_activity"
        const val APPLICATION = "/submarine/application"
        const val RECEPTION_DOC = "/submarine/reception_doc"      //接待建档
        const val FOLLOW_UP = "/submarine/follow_up"              //潜客跟进
        const val NEWS_LETTER = "/submarine/news_letter"              //今日简报

        const val DEFEATED_AUDIT = "/submarine/defeated_audit"              //战败审核
        const val CUSTOMER_INFO = "/submarine/customer_info"              //客户信息
        const val CUSTOMER_INFO_PC_NO = "/submarine/customer_info_pc_no"
    }
}