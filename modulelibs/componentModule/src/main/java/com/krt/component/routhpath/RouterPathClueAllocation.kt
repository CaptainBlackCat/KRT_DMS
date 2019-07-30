package com.krt.component.routhpath

//线索分配
interface RouterPathClueAllocation {
    companion object {
        const val TEST_ACTIVITY = "/clueAllocation/test_activity"
        const val APPLICATION = "/clueAllocation/application"
        const val CLUE_ALLOCATION_PAGE = "/clueAllocation/page"       //线索分配

        const val POTENTIAL_CUSTOMERS = "/clueAllocation/potential_customers"              //潜在客户 等级索引
        const val POTENTIAL_CUSTOMERS_LEADS_LEVEL = "leads_level"
        const val POTENTIAL_CUSTOMERS_EMD_ID = "emp_id"
    }
}