package com.krt.home.homepage

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.base.ext.handlerDelay
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.business.db.AppPostShow
import com.krt.business.ext.toCustom
import com.krt.component.ModuleServiceFactory
import com.krt.component.routhpath.RouterPathHome
import com.krt.frame.app.config.KRT
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.home.R
import com.krt.home.homepage.bean.HomeAdapterAllEntity
import kotlinx.android.synthetic.main.home_fragment_home_page.*

@Route(path = RouterPathHome.HOME_PAGE, name = "Home模块首页入口")
class HomePageFragment : BaseLceFragment<HomePageViewModel>() {

    private lateinit var mAdapter: HomePageMultipleItemAdapter

    private val mItemList = ArrayList<HomeAdapterAllEntity>()

    override fun initToolBarConfig(): ToolBarConfig = ToolBarConfig(
            R.layout.home_fragment_home_page,
            toolBarStyle = ToolBarStyle.NORMAL,
            leftViewStyle = ToolBarViewStyle.ICON,
            middleTitleRes = R.string.home_page,
            leftViewIcon = R.drawable.bus_nav_ic_menu,
            leftViewClickListener = {
                ModuleServiceFactory.instance.appService?.openNavigationView()
            }
    ).toCustom(
            customToolBarBackGround = true
    )

    override fun initView() {
        if (AppPostShow.isCurrentPostAdviserShow()) {
            //顾问
            mItemList.add(HomeAdapterAllEntity(HomePageMultipleItemAdapter.MONTHLY_PERFORMANCE))
        } else {
            //经理
            mItemList.add(HomeAdapterAllEntity(HomePageMultipleItemAdapter.TODAY_PERFORMANCE))
        }

        mItemList.add(HomeAdapterAllEntity(HomePageMultipleItemAdapter.FUNCTION_CHOOSE))

        if (AppPostShow.isCurrentPostAdviserShow()) {
            //顾问
        } else {
            //经理
            mItemList.add(HomeAdapterAllEntity(HomePageMultipleItemAdapter.SUBMARINES_SCHEMATIC_DIAGRAM))
        }

        mItemList.add(HomeAdapterAllEntity(HomePageMultipleItemAdapter.SUBMARINES))

        mAdapter = HomePageMultipleItemAdapter(this, mItemList)

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView, autoStartRefresh = false)
        {
            mAdapter.notifyDataSetChanged()
            base_lceRefreshView.isRefreshing = false
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        //防止动画的卡顿
        handlerDelay(KRT.getFragmentAnimSkipTime()) {
            mAdapter.refreshSubmarinesItemProvider()
        }
    }

}