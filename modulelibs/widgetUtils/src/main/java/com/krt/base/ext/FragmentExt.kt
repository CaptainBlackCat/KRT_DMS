package com.krt.base.ext

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.krt.base.R
import com.krt.frame.pullrecyclerview.AutoSwipeRefreshView
import com.krt.frame.pullrecyclerview.ColorDividerItemDecoration
import com.krt.frame.pullrecyclerview.PullRecyclerView


/**
 * RecyclerView下拉刷新配合的是SwipeRefreshLayout
 */
fun Fragment.initSwipeRefreshLayout(
        adapter: RecyclerView.Adapter<*>,   //适配器
        base_lceRefreshView: SwipeRefreshLayout,
        base_lceRecyclerView: RecyclerView,
        autoStartRefresh: Boolean = true,   //初始化完后是否自动刷新
        itemDecorationEnabled: Boolean = false,    //是否添加分割线
        isEmptyViewEnabled: Boolean = true,   //数据为空时，是否要显示数据为空的提示
        emptyText: String? = null,     //数据为空时显示内容
        isLinearLayout: Boolean = true,
        spanCount: Int = 2,
        actionLoadMore: (() -> Unit)? = null,    //加载更多
        actionRefresh: () -> Unit
) {
    base_lceRefreshView.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
    )
    initCommonProperty(
            base_lceRecyclerView,
            isLinearLayout,
            activity,
            spanCount,
            adapter,
            itemDecorationEnabled,
            isEmptyViewEnabled,
            emptyText,
            context
    ) {
        base_lceRefreshView.isRefreshing = true
        actionRefresh.invoke()
    }

    base_lceRefreshView as AutoSwipeRefreshView

    base_lceRefreshView.action = actionRefresh

    base_lceRefreshView.setOnRefreshListener {
        actionRefresh.invoke()
    }

    actionLoadMore?.let {
        if (adapter is BaseQuickAdapter<*, *>) {
            adapter.setEnableLoadMore(true)
            adapter.setLoadMoreView(SimpleLoadMoreView())
            adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                actionLoadMore.invoke()
            }, base_lceRecyclerView)

        } else {
            throw ClassCastException("if you want load more, you must set adapter extend BaseQuickAdapter !!!!!!!!!")
        }
    }

    if (autoStartRefresh) {
        base_lceRefreshView.refresh()
    }
}

/**
 * RecyclerView下拉刷新，自定义用的PullRecyclerView
 */
fun Fragment.initPullRefreshLayout(
        adapter: RecyclerView.Adapter<*>,   //适配器
        base_lceRecyclerView: PullRecyclerView,
        autoStartRefresh: Boolean = true,   //初始化完后是否自动刷新
        itemDecorationEnabled: Boolean = false,    //是否添加分割线
        isEmptyViewEnabled: Boolean = true,   //数据为空时，是否要显示数据为空的提示
        emptyText: String? = null,     //数据为空时显示内容
        isLinearLayout: Boolean = true,
        spanCount: Int = 2,
        actionLoadMore: (() -> Unit)? = null,    //加载更多
        actionRefresh: () -> Unit
) {
    initCommonProperty(
            base_lceRecyclerView,
            isLinearLayout,
            activity,
            spanCount,
            adapter,
            itemDecorationEnabled,
            isEmptyViewEnabled,
            emptyText,
            context
    ) {
        base_lceRecyclerView.refresh()
    }

    base_lceRecyclerView.initPullRefreshListener(object : PullRecyclerView.LoadingListener {
        override fun onRefresh() {
            actionRefresh.invoke()
        }
    })

    actionLoadMore?.let {
        if (adapter is BaseQuickAdapter<*, *>) {
            adapter.setEnableLoadMore(true)
            adapter.setLoadMoreView(SimpleLoadMoreView())
            adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                actionLoadMore.invoke()
            }, base_lceRecyclerView)

        } else {
            throw ClassCastException("if you want load more, you must set adapter extend BaseQuickAdapter !!!!!!!!!")
        }
    }

    if (autoStartRefresh) {
        base_lceRecyclerView.refresh()
    }
}

private fun initCommonProperty(
        recyclerView: RecyclerView,
        isLinearLayout: Boolean,
        activity: FragmentActivity?,
        spanCount: Int,
        adapter: RecyclerView.Adapter<*>,
        itemDecorationEnabled: Boolean,
        isEmptyViewEnabled: Boolean,
        emptyText: String?,
        context: Context?,
        actionRefresh: () -> Unit
) {
    if (isLinearLayout)
        recyclerView.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
    else
        recyclerView.layoutManager = GridLayoutManager(activity, spanCount)

    recyclerView.adapter = adapter

    if (itemDecorationEnabled) {
        recyclerView.addItemDecoration(ColorDividerItemDecoration())
    }

    if (isEmptyViewEnabled && adapter is BaseQuickAdapter<*, *>) {
        val emptyView = View.inflate(context, R.layout.base_empty_view, null)
        emptyText?.let {
            emptyView.findViewById<TextView>(R.id.warn).text = it
        }
        adapter.emptyView = emptyView
        adapter.isUseEmpty(false)

        emptyView.setOnClickListener {
            actionRefresh.invoke()
        }
    }
    //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
    recyclerView.setHasFixedSize(true)
}
