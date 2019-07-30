package com.krt.submarine

import com.alibaba.android.arouter.launcher.ARouter
import com.krt.component.routhpath.RouterPathSubmarine
import com.krt.component.service.ISubmarineModuleService
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.submarine.customerinfo.CustomerInfoFragment


class SubmarineModuleService : ISubmarineModuleService {

    /**
     * 跳转到潜客跟进页面
     */
    override fun direct2SubmarineFollowUpContainerFragment(fragment: BaseFragment, position: Int) {
        (ARouter.getInstance().build(RouterPathSubmarine.FOLLOW_UP).withInt(
                "position", position
        ).navigation() as? BaseFragment)?.let {
            fragment.start(it)
        }
    }

    /**
     * 跳转到 战败待审核
     */
    override fun direct2DefeatAuditListFragment(fragment: BaseFragment) {
        (ARouter.getInstance().build(RouterPathSubmarine.DEFEATED_AUDIT).navigation() as? BaseFragment)?.let {
            fragment.start(it)
        }
    }

    /**
     * 跳转到   客户信息界面
     */
    override fun direct2SubmarineCustomerInfoFragment(fragment: BaseFragment, pcNo: String) {
        fragment.start(CustomerInfoFragment.newInstance(pcNo))
    }

}