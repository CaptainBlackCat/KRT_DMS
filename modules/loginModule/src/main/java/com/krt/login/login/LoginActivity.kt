package com.krt.login.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathLogin
import com.krt.frame.frame.activity.BaseActivity
import com.krt.frame.frame.fragment.BaseFragment


@Route(path = RouterPathLogin.LOGIN_MAIN)
class LoginActivity : BaseActivity() {

    override fun getRootFragment(): BaseFragment {
        return LoginFragment()
    }

}