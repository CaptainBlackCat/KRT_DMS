package com.krt.clue.debug

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.clue.clueallocation.page.ClueAllocationListFragment
import com.krt.component.routhpath.RouterPathClueAllocation
import com.krt.frame.frame.activity.BaseActivity
import com.krt.frame.frame.fragment.BaseFragment

@Deprecated("潜客跟进")
@Route(path = RouterPathClueAllocation.TEST_ACTIVITY)
class ClueAllocationTestActivity : BaseActivity() {

    //线索分配详情
//    override fun getRootFragment(): BaseFragment = ClueAllocationSearchFragment.newInstance("1")

    //线索分配列表
    override fun getRootFragment(): BaseFragment = ClueAllocationListFragment()

    //客户等级列表
//    override fun getRootFragment(): BaseFragment = CustomerLevelListFragment()

    //客户等级详情页
//    override fun getRootFragment(): BaseFragment = CustomerLevelDetailFragment.newInstance(30440005)

//    override fun getRootFragment(): BaseFragment = SaleConsultantListFragment.newInstance(true)

}