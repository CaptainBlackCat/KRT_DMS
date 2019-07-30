package com.krt.component.service

import com.krt.frame.app.IModuleService
import com.krt.frame.frame.fragment.BaseFragment

/**
 * Created by xbf on 2018/8/7
 */
interface ISubmarineModuleService : IModuleService {

    /**
     * 跳转到潜客跟进页面
     */
    fun direct2SubmarineFollowUpContainerFragment(fragment: BaseFragment, position: Int)

    /**
     * 跳转到 战败待审核
     */
    fun direct2DefeatAuditListFragment(fragment: BaseFragment)

    /**
     * 跳转到   客户信息界面
     */
    fun direct2SubmarineCustomerInfoFragment(fragment: BaseFragment, pcNo: String)
}
