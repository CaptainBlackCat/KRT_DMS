package com.krt.submarine.record.history

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.business.ext.toCustom
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.submarine.R
import com.krt.submarine.record.history.adapter.HistoryRecordAdapter
import kotlinx.android.synthetic.main.sub_fragment_recycler_view.*
import org.jetbrains.anko.bundleOf

class HistoryRecordFragment : BaseLceFragment<HistoryRecordViewModel>() {

    private lateinit var mAdapter: HistoryRecordAdapter

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_recycler_view,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "历史记录"
    ).toCustom(customAll = true)

    override fun initViewModelLiveData() {
        viewModel?.listDataLiveData?.observe(this, Observer {
            //            it?.let {
            mAdapter.setNewData(it)
//            }
        })
    }

    override fun initView() {
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        if (arguments == null) {
            return
        }
        val phone = arguments!!.getString(PHONE)

        mAdapter = HistoryRecordAdapter()

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView) {
            viewModel?.loadData(phone ?: "")
        }
    }

    companion object {

        private const val PHONE = "phone"

        fun newInstance(phone: String): HistoryRecordFragment {
            val fragment = HistoryRecordFragment()
            fragment.arguments = bundleOf(Pair(PHONE, phone))
            return fragment
        }
    }

}