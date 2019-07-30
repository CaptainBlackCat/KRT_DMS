package com.krt.submarine.customerstatuslist

import android.arch.lifecycle.Observer
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.business.ext.toCustom
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.submarine.R
import com.krt.submarine.config.SubmarineModuleConfig
import com.krt.submarine.customerstatuslist.adapter.CustomerFollowUpStatusAdapter
import kotlinx.android.synthetic.main.sub_fragment_news_letter.*
import org.jetbrains.anko.bundleOf

//今日已完成客户、今日未完成客户、今日应跟进客户
class CustomerFollowUpStatusFragment : BaseLceFragment<CustomerFollowUpStatusViewModel>() {

    private lateinit var mAdapter: CustomerFollowUpStatusAdapter

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_swipe_recycler_view,
            toolBarStyle = ToolBarStyle.NORMAL
    ).toCustom(customAll = true)

    override fun initViewModelLiveDataAfterAnimationEnd() {
        super.initViewModelLiveDataAfterAnimationEnd()
        viewModel?.listDataLiveData?.observe(this, Observer {
            it?.let { data ->
                mAdapter.setNewData(data)
            }
        })
    }

    override fun initView() {
        if (arguments == null) {
            return
        }

        val followUpStatus = arguments!!.getInt(FOLLOW_UP_STATUS)
        val name = arguments!!.getString(FOLLOW_UP_NAME) ?: ""

        toolBarConfigHelper.getTitleView().text = when (followUpStatus) {
            SubmarineModuleConfig.FOLLOW_UP_STATUS_NEED -> {
                "今日应跟进客户"
            }
            SubmarineModuleConfig.FOLLOW_UP_STATUS_FINISH -> {
                "今日已完成客户"
            }
            SubmarineModuleConfig.FOLLOW_UP_STATUS_INCOMPLETE -> {
                "今日未完成客户"
            }
            else -> {
                ""
            }
        }

        mAdapter = CustomerFollowUpStatusAdapter()

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView, itemDecorationEnabled = true)
        {
            viewModel?.loadData(name, followUpStatus)
        }
    }

    companion object {

        private const val FOLLOW_UP_STATUS = "follow_up_status"
        private const val FOLLOW_UP_NAME = "follow_up_name"

        fun newInstance(status: Int, name: String): CustomerFollowUpStatusFragment {
            val fragment = CustomerFollowUpStatusFragment()
            fragment.arguments = bundleOf(Pair(FOLLOW_UP_STATUS, status), Pair(FOLLOW_UP_NAME, name))
            return fragment
        }
    }

}