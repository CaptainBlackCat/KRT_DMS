package com.krt.submarine.followup

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.base.ext.postSticky
import com.krt.base.ext.unPostSticky
import com.krt.business.ext.toCustom
import com.krt.component.routhpath.RouterPathSubmarine
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.submarine.R
import com.krt.submarine.followup.eventbus.SubmarineFollowUpContainerEventBus
import com.krt.submarine.followup.within.SubmarineFollowUpFragment
import kotlinx.android.synthetic.main.sub_fragment_submarine_follow_up.*

@Route(path = RouterPathSubmarine.FOLLOW_UP, name = "潜客跟进")
class SubmarineFollowUpContainerFragment : BaseFragment() {

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_submarine_follow_up,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "潜客跟进"
    ).toCustom(customAll = true)

    override fun initView() {
        tab_layout.setupWithViewPager(view_pager)

        view_pager.offscreenPageLimit = 3
        val titles = resources.getStringArray(R.array.sub_submarine_tab_strings)
        view_pager.adapter = PagerFragmentAdapter(childFragmentManager, titles)

        arguments?.getInt("position")?.let {
            if (it > 0) {
                view_pager.setCurrentItem(it, false)
            }
        }
    }

    private val eventBus = SubmarineFollowUpContainerEventBus()

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

    @SuppressLint("SetTextI18n")
    fun updateDataCount(count: Int, status: Int) {
        when (status) {
            SubmarineFollowUpConfig.TWO_DAYS_WITHIN -> {
                val titleText =
                        ((tab_layout.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(1) as TextView
                titleText.text = "2天内($count)"
            }
            SubmarineFollowUpConfig.TWO_DAYS_AFTER -> {
                val titleText =
                        ((tab_layout.getChildAt(0) as LinearLayout).getChildAt(1) as LinearLayout).getChildAt(1) as TextView

                titleText.text = "2天后($count)"
            }
            SubmarineFollowUpConfig.TIME_OUT -> {
                val titleText =
                        ((tab_layout.getChildAt(0) as LinearLayout).getChildAt(2) as LinearLayout).getChildAt(1) as TextView
                titleText.text = "超时(($count)"
            }
        }
    }

    inner class PagerFragmentAdapter(fm: FragmentManager, val titles: Array<String>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    val fragment =
                            SubmarineFollowUpFragment.newInstance(SubmarineFollowUpConfig.TWO_DAYS_WITHIN)
                    fragment
                }
                1 -> {
                    val fragment =
                            SubmarineFollowUpFragment.newInstance(SubmarineFollowUpConfig.TWO_DAYS_AFTER)
                    fragment
                }
                else -> {
                    val fragment =
                            SubmarineFollowUpFragment.newInstance(SubmarineFollowUpConfig.TIME_OUT)
                    fragment
                }
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }


        override fun getCount(): Int {
            return 3
        }
    }

}