package com.krt.submarine.debug

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathSubmarine
import com.krt.frame.frame.activity.BaseActivity
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.submarine.followup.SubmarineFollowUpContainerFragment

@Deprecated("潜客跟进")
@Route(path = RouterPathSubmarine.TEST_ACTIVITY)
class SubmarineTestActivity : BaseActivity() {

    //接待建档
//    override fun getRootFragment(): BaseFragment = ReceptionDocumentFragment()

    //潜客跟进列表
//    override fun getRootFragment(): BaseFragment = CustomerInfoFragment.newInstance(/*"CC2018121004"*/"CC2018121701")

//    override fun getRootFragment(): BaseFragment = DefeatAuditListFragment()

//    override fun getRootFragment(): BaseFragment = DefeatAuditDetailFragment.newInstance("三生")

    override fun getRootFragment(): BaseFragment = SubmarineFollowUpContainerFragment()

//    override fun getRootFragment(): BaseFragment =
//        NewFollowUpFragment.newInstance("字段全", 30440005, "SC6388CVBEV", "SC6388CVBEV.H3D1", "SA8", 1544543940000,"CC2018121004)

//    override fun getRootFragment(): BaseFragment = CustomerNameFragment()

//    override fun getRootFragment(): BaseFragment = DefeatAuditDetailFragment.newInstance("焦仲卿")

//    override fun getRootFragment(): BaseFragment = SendSmsFragment()

//    override fun getRootFragment(): BaseFragment = SubDefeatReasonFragment.newInstance()
}