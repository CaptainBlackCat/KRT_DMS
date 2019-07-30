package com.krt.clue

import com.alibaba.android.arouter.launcher.ARouter
import com.krt.component.routhpath.RouterPathClueAllocation
import com.krt.component.service.IClueAllocationModuleService
import com.krt.frame.frame.fragment.BaseFragment

class ClueAllocationModuleService : IClueAllocationModuleService {

    /**
     * 跳转到 线索待分配
     */
    override fun direct2ClueAllocationFragment(fragment: BaseFragment) {
        (ARouter.getInstance().build(RouterPathClueAllocation.CLUE_ALLOCATION_PAGE).navigation() as? BaseFragment)?.let {
            fragment.start(it)
        }
    }
}