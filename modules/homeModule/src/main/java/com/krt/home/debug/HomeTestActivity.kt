package com.krt.home.debug

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathHome
import com.krt.frame.frame.activity.BaseActivity
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.home.testdrive.comment.CommentFragment

@Deprecated("潜客跟进")
@Route(path = RouterPathHome.TEST_ACTIVITY)
class HomeTestActivity : BaseActivity() {

//    override fun getRootFragment(): BaseFragment = TestDriveListContainerFragment()

//    override fun getRootFragment(): BaseFragment = StartTestDriveFragment.newInstance("TD2018121301")

    override fun getRootFragment(): BaseFragment = CommentFragment.newInstance("TD2018121301", -1, true)

}