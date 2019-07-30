package com.krt.submarine.defeataudit.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.ext.toCustom
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.submarine.R
import com.krt.submarine.defeataudit.detail.adapter.DefeatAuditDetailAdapter
import com.krt.submarine.defeataudit.eventbus.DefeatAuditEventBus
import com.krt.submarine.defeataudit.examine.DefeatAuditExamineFragment
import kotlinx.android.synthetic.main.sub_fragment_news_letter.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.bundleOf

class DefeatAuditDetailFragment : BaseLceFragment<DefeatAuditDetailViewModel>() {

    private lateinit var mAdapter: DefeatAuditDetailAdapter

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_swipe_recycler_view,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "战败待审核"
    ).toCustom(customAll = true)

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.listDataLiveData?.observe(this, Observer {
            it?.let { data ->
                mAdapter.setNewData(data)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InformationDataConfig.loadInformationData()
    }

    override fun initView() {
        if (arguments == null) {
            return
        }

        val name = arguments!!.getString(FOLLOW_UP_NAME) ?: ""

        mAdapter = DefeatAuditDetailAdapter(this)
        mAdapter.actionDirect2Examine = { style, foNo ->
            start(DefeatAuditExamineFragment.newInstance(style, foNo))
        }

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView, itemDecorationEnabled = true)
        {
            viewModel?.loadData(name)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onContainerAnimationEnd(event: DefeatAuditEventBus) {
        base_lceRefreshView.refresh()
    }

    companion object {

        private const val FOLLOW_UP_NAME = "follow_up_name"

        fun newInstance(name: String): DefeatAuditDetailFragment {
            val fragment = DefeatAuditDetailFragment()
            fragment.arguments = bundleOf(Pair(FOLLOW_UP_NAME, name))
            return fragment
        }
    }

}