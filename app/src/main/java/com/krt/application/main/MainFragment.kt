package com.krt.application.main

import com.alibaba.android.arouter.launcher.ARouter
import com.krt.application.R
import com.krt.application.activity.MainActivity
import com.krt.component.routhpath.RouterPathHome
import com.krt.component.routhpath.RouterPathRemind
import com.krt.frame.app.AppManager
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.navigation.BaseNavigationFragment
import com.krt.frame.navigation.NavigationItemBuilder
import com.krt.frame.navigation.bean.NavigationTabBean
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseNavigationFragment<MainViewModel>() {

    private var TOUCH_TIME: Long = 0

    override fun initToolBarConfig() = ToolBarConfig(R.layout.fragment_main)

    override fun setItems(builder: NavigationItemBuilder): LinkedHashMap<NavigationTabBean, BaseFragment> {
        val items = LinkedHashMap<NavigationTabBean, BaseFragment>()
        addModule(items, RouterPathHome.HOME_PAGE, R.string.app_item_home, R.drawable.nav_ic_home_bg)
        addModule(items, RouterPathRemind.REMIND_PAGE, R.string.app_item_remind, R.drawable.nav_ic_warn_bg)
        return builder.addItems(items).build()
    }

    override fun setIndexDelegate(): Int = 0

    override fun getBottomContainer() = this.bottom_bar!!

    override fun getFragmentContainerId() = R.id.main_content_container

    private fun addModule(items: LinkedHashMap<NavigationTabBean, BaseFragment>, routerPath: String, moduleName: Int, drawableId: Int) {
        ARouter.getInstance().build(routerPath).navigation()?.let {
            items[NavigationTabBean(drawableId, getString(moduleName))] = it as BaseFragment
        }
    }

    override fun onBackPressedSupport(): Boolean {
        val mCurrentActivity = activity as MainActivity

        if ((mCurrentActivity.isDrawerLayoutOpen())) {
            mCurrentActivity.closeDrawerLayoutOpen()
            return true
        }

        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            AppManager.finishAllActivity()
        } else {
            TOUCH_TIME = System.currentTimeMillis()
            showToast("双击退出" + getString(R.string.app_name))
        }

        return true
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        val mCurrentActivity = activity as MainActivity
        mCurrentActivity.enableDrawer(true)
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        val mCurrentActivity = activity as MainActivity
        mCurrentActivity.enableDrawer(false)
    }

    companion object {

        // 再点一次退出程序时间设置
        private const val WAIT_TIME = 2000L

    }

}