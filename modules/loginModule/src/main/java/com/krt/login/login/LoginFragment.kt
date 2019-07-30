package com.krt.login.login

import android.arch.lifecycle.Observer
import android.text.SpannableStringBuilder
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.SPUtils
import com.krt.base.ext.yes
import com.krt.component.routhpath.RouterPathMain
import com.krt.frame.ext.onClick
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.login.BuildConfig
import com.krt.login.R
import kotlinx.android.synthetic.main.login_fragment_login.*


class LoginFragment : BaseLceFragment<LoginViewModel>() {

    override fun initToolBarConfig(): ToolBarConfig = ToolBarConfig(R.layout.login_fragment_login)

    override fun initViewModelLiveData() {
        viewModel?.loginSuccessLiveData?.observe(this, Observer {
            it?.yes {
                ARouter.getInstance().build(RouterPathMain.MAIN_ACTIVITY).navigation()
                activity?.finish()
            }
        })
    }

    override fun initView() {
        val companyCode = SPUtils.getInstance().getString(LoginViewModel.COMPANY_CODE)
        val userCode = SPUtils.getInstance().getString(LoginViewModel.USER_CODE)

        login_company.text = SpannableStringBuilder(companyCode)
        login_account.text = SpannableStringBuilder(userCode)

        if (BuildConfig.DEBUG) {
            login_company.text = SpannableStringBuilder(BuildConfig.testCompanyCode)
            login_account.text = SpannableStringBuilder(BuildConfig.testUserName)
            login_password.text = SpannableStringBuilder(BuildConfig.testPassword)
        }
    }

    override fun initViewClickListener() {
        super.initViewClickListener()

        login_btn_sign_in.onClick {
            val companyCode = login_company.text.toString().trim()
            val account = login_account.text.toString().trim()
            val password = login_password.text.toString().trim()

            if (companyCode.isEmpty()) {
                showToast("请输入公司代码")
                return@onClick
            }

            if (account.isEmpty()) {
                showToast("请输入用户名")
                return@onClick
            }

            if (password.isEmpty()) {
                showToast("请输入密码")
                return@onClick
            }
//            ARouter.getInstance().build(RouterPathMain.MAIN_ACTIVITY).navigation()
//            activity?.finish()
            viewModel?.login(companyCode, account, password)
        }
    }

}