package com.krt.category

import com.krt.component.service.ICategoryModuleService

class CategoryModuleService : ICategoryModuleService {
    override fun testDelay1(a1: String) {
    }

    override fun testDelay2(a1: String, a2: String) {
    }

    override fun testDelay(aaa: String): String {
        return "123"
    }
}