package com.krt.application.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.krt.application.AppModuleService
import com.krt.application.MainApplication
import com.krt.application.R
import com.krt.application.main.MainFragment
import com.krt.base.ext.*
import com.krt.business.GlobalContacts
import com.krt.business.db.AppPostShow
import com.krt.business.user.UserDefault
import com.krt.component.routhpath.RouterPathLogin
import com.krt.component.routhpath.RouterPathMain
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.ext.showToast
import com.krt.frame.frame.activity.BaseLceActivity
import com.krt.frame.frame.mvvm.LceEmpty
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportFragment

@Route(path = RouterPathMain.MAIN_ACTIVITY)
class MainActivity : BaseLceActivity<MainActivityViewModel>() {

    private lateinit var mHeadView: View

    override fun getRootFragment(): Nothing? = null

    private var mFragment: SupportFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initView()
        initClickListener()

        //如果缓存里没有设置，比如重新登入
        if (AppPostShow.isCurrentPostEmpty()) {
            if (UserDefault.isUserSalesManager()) {
                switch2Manager()
            } else {
                switch2Adviser()
            }
        } else {
            if (AppPostShow.isCurrentPostAdviserShow()) {
                switch2Adviser()
            } else {
                switch2Manager()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as MainApplication).appModuleService.navigationCallBack
    }

    override fun initViewModelLiveData() {
        viewModel?.logoutSuccessLiveData?.observe(this, Observer {
            it?.yes {
                try {
                    ARouter.getInstance().build(RouterPathLogin.LOGIN_MAIN).navigation()
                    finish()
                } catch (e: Exception) {
                    showToast(e.toString())
                }
            }
        })
    }

    private fun initView() {
        mHeadView = nav_view.getHeaderView(0)
        nav_view.itemIconTintList = null

        (application as MainApplication).appModuleService.navigationCallBack = object :
                AppModuleService.INavigationCallBack {
            override fun open() {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun initClickListener() {
        mHeadView.findViewById<View>(R.id.main_nav_job_switching).onClick {
            drawer_layout.closeDrawer(GravityCompat.START)
            handlerDelay(NAVIGATION_VIEW_CLOSING_TIME) {
                switchPost()
            }
        }

        mHeadView.findViewById<View>(R.id.main_nav_logout).onClick {
            drawer_layout.closeDrawer(GravityCompat.START)
            handlerDelay(NAVIGATION_VIEW_CLOSING_TIME) {
                viewModel?.logout()
            }
        }
    }

    /**
     * 销售经理
     */
    private fun switch2Manager() {
        mFragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }

        val fragment = MainFragment()
        loadRootFragment(R.id.fl_container, fragment, false, false)
        mFragment = fragment

        AppPostShow.setCurrentPostManager()

        updateNavigationView()
    }

    /**
     * 销售顾问
     */
    private fun switch2Adviser() {
        mFragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }

        val fragment = MainFragment()
        loadRootFragment(R.id.fl_container, fragment, false, false)
        mFragment = fragment

        AppPostShow.setCurrentPostAdviser()

        updateNavigationView()
    }

    private fun switchPost() {
        if (AppPostShow.isCurrentPostAdviserShow()) {
            switch2Manager()
        } else {
            switch2Adviser()
        }
    }

    fun enableDrawer(enabled: Boolean) {
        enabled.yes {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }.otherwise {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    fun isDrawerLayoutOpen(): Boolean {
        return drawer_layout.isDrawerOpen(GravityCompat.START)
    }

    fun closeDrawerLayoutOpen() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    private fun updateNavigationView() {
        UserDefault.headPortrait.let {
            mHeadView.findViewById<ImageView>(R.id.nav_img_header)?.loadImage(this, it, USER_HEAD_DEFAULT_PIC)
        }

        mHeadView.findViewById<TextView>(R.id.nav_tv_name).text = UserDefault.empName
        mHeadView.findViewById<TextView>(R.id.duty_name).text = UserDefault.dutyNameCN

        //如果是销售经理
        if (UserDefault.dutyId == GlobalContacts.SALES_MANAGER_ID) {
            mHeadView.findViewById<TextView>(R.id.main_nav_job_switching).setVisible(View.VISIBLE)
            mHeadView.findViewById<TextView>(R.id.main_nav_job_switching_line).setVisible(View.VISIBLE)
        } else {
            mHeadView.findViewById<TextView>(R.id.main_nav_job_switching).setVisible(View.GONE)
            mHeadView.findViewById<TextView>(R.id.main_nav_job_switching_line).setVisible(View.GONE)
        }
    }

    override fun showEmpty(lceEmpty: LceEmpty) {
    }


    companion object {
        private const val NAVIGATION_VIEW_CLOSING_TIME = 300L
    }
}
