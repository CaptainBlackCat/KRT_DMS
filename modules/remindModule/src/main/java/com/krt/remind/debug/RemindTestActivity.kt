package com.krt.remind.debug

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathRemind
import com.krt.frame.frame.activity.BaseActivity
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.remind.remind.RemindPageFragment

@Deprecated("潜客跟进")
@Route(path = RouterPathRemind.TEST_ACTIVITY)
class RemindTestActivity : BaseActivity() {

    override fun getRootFragment(): BaseFragment = RemindPageFragment()
}