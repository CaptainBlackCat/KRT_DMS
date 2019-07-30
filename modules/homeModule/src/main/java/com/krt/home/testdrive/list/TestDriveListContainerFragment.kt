package com.krt.home.testdrive.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.base.ext.getColor
import com.krt.base.ext.postSticky
import com.krt.base.ext.unPostSticky
import com.krt.business.ext.toCustom
import com.krt.component.routhpath.RouterPathTestDrive
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.home.R
import com.krt.home.testdrive.createmodify.NewOrModifyFragment
import com.krt.home.testdrive.list.eventbus.TestDriveContainerEventBus
import kotlinx.android.synthetic.main.home_fragment_test_drive_container.*

@Route(path = RouterPathTestDrive.TEST_DRIVE_LIST_CONTAINER, name = "试乘试驾")
class TestDriveListContainerFragment : BaseFragment() {

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.home_fragment_test_drive_container,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "试乘试驾",
            rightViewTextFontColor = getColor(R.color.base_white),
            rightViewText = "新增",
            rightViewClickListener =
            {
                start(NewOrModifyFragment.newInstance())
            }
    ).toCustom(
            customBackPop = true
    )

    override fun initView() {
        tab_layout.setupWithViewPager(view_pager)

        view_pager.offscreenPageLimit = 5
        val titles = resources.getStringArray(R.array.home_test_drive_tab_strings)
        view_pager.adapter = PagerFragmentAdapter(childFragmentManager, titles)

        arguments?.getInt("position")?.let {
            if (it > 0) {
                view_pager.setCurrentItem(it, false)
            }
        }
    }

    private val eventBus = TestDriveContainerEventBus()

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        postSticky(eventBus)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unPostSticky(eventBus)
    }

    override fun onBackPressedSupport(): Boolean {
        this.pop()
        return true
    }

    inner class PagerFragmentAdapter(fm: FragmentManager, private val titles: Array<String>) :
            FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> TestDriveListFragment.newInstance(TestDriveListConfig.ALL)
                1 -> TestDriveListFragment.newInstance(TestDriveListConfig.QUEUE)
                2 -> TestDriveListFragment.newInstance(TestDriveListConfig.DRIVEING)
                3 -> TestDriveListFragment.newInstance(TestDriveListConfig.TOCOMMENT)
                else -> TestDriveListFragment.newInstance(TestDriveListConfig.FINISH)
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }


        override fun getCount(): Int {
            return titles.size
        }
    }
}