package com.krt.component.service

import com.krt.frame.app.IModuleService
import com.krt.frame.frame.fragment.BaseFragment

interface IClueAllocationModuleService : IModuleService {

    /**
     * 跳转到 线索待分配
     */
    fun direct2ClueAllocationFragment(fragment: BaseFragment)

}