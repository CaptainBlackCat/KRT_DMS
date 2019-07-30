package com.krt.component.service

import com.krt.frame.app.IModuleService
import com.krt.frame.frame.fragment.BaseFragment

/**
 * Created by xbf on 2018/8/7
 */
interface IHomeModuleService : IModuleService {

    /**
     * 跳转到 试乘试驾 页面
     */
    fun direct2TestDriveContainerFragment(fragment: BaseFragment, position: Int)


}
