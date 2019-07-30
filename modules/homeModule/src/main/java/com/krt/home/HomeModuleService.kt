package com.krt.home

import com.alibaba.android.arouter.launcher.ARouter
import com.krt.component.routhpath.RouterPathTestDrive
import com.krt.component.service.IHomeModuleService
import com.krt.frame.frame.fragment.BaseFragment

class HomeModuleService : IHomeModuleService {

    /**
     * 跳转到 试乘试驾 页面
     */
    override fun direct2TestDriveContainerFragment(fragment: BaseFragment, position: Int) {
        (ARouter.getInstance().build(RouterPathTestDrive.TEST_DRIVE_LIST_CONTAINER).withInt(
                "position", position
        ).navigation() as? BaseFragment)?.let {
            fragment.start(it)
        }
    }


}