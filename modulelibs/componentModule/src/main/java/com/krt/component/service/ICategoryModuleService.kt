package com.krt.component.service

import com.krt.frame.app.IModuleService

/**
 * Created by xbf on 2018/8/7
 */
interface ICategoryModuleService : IModuleService {

    fun testDelay(aaa: String): String

    fun testDelay1(a1: String)

    fun testDelay2(a1: String, a2: String)

}
