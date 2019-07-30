package com.krt.submarine.newsletter

import android.arch.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.business.ext.toCustom
import com.krt.component.routhpath.RouterPathSubmarine
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.submarine.R
import com.krt.submarine.customerstatuslist.CustomerFollowUpStatusFragment
import com.krt.submarine.newsletter.adapter.NewsLetterAdapter
import kotlinx.android.synthetic.main.sub_fragment_news_letter.*

@Route(path = RouterPathSubmarine.NEWS_LETTER)
class NewsLetterFragment : BaseLceFragment<NewsLetterViewModel>() {

    private lateinit var mAdapter: NewsLetterAdapter

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_news_letter,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "今日简报"
    ).toCustom(customAll = true)

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.listDataLiveData?.observe(this, Observer {
            mAdapter.setNewData(it)
        })
    }

    override fun initView() {
        mAdapter = NewsLetterAdapter()

        mAdapter.actionClick = { status, entity ->
            val fragment = CustomerFollowUpStatusFragment.newInstance(status, entity.foBy)
            start(fragment)
        }

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView, itemDecorationEnabled = true)
        {
            viewModel?.loadData()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAdapter.actionClick = null
    }

    override fun onBackPressedSupport(): Boolean {
        this.pop()
        return true
    }

}