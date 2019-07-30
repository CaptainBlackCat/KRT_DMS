package com.krt.application.splash

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SPUtils
import com.krt.business.GlobalContacts
import com.krt.business.user.UserDefault
import com.krt.component.routhpath.RouterPathLogin
import com.krt.component.routhpath.RouterPathMain
import com.krt.frame.ext.showToast
import com.tbruyelle.rxpermissions2.RxPermissions


/**
 * 用这个命令行，检测APP启动速度
 * adb shell am start -W com.necj.application/com.necj.app.SplashActivity
 *
 *
 * Monkey  测试
 *
 * adb shell monkey -p com.krt.application.dms –-throttle 300 10000
 *
 *throttle 为点击速率   ：：一般设置为300毫秒，原因是实际用户操作的最快300毫秒左右一个动作事件
 *
 */
class SplashActivity : AppCompatActivity() {

    private var startTime = System.currentTimeMillis()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Apk is first create->open->Home to table->click the icon:   the Apk will start second
        if (!isTaskRoot) {
            finish()
            return
        }


        if (!SPUtils.getInstance().getBoolean(GlobalContacts.IS_NOT_FIRST_LOGIN)) {
            SPUtils.getInstance().put(GlobalContacts.IS_NOT_FIRST_LOGIN, true)

//            setContentView(R.layout.activity_splash)

            RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {
                start()
            }
        } else {
            val time = 400 - (System.currentTimeMillis() - startTime)

//            Handler().postDelayed({
            start()
//            }, time)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        KeyboardUtils.fixSoftInputLeaks(this)
    }

    private fun start() {
        val isUserNotEmpty = UserDefault.readUserFromDataBase()

        if (isUserNotEmpty) {
            //TODO
            ARouter.getInstance().build(RouterPathMain.MAIN_ACTIVITY).navigation()

//            ARouter.getInstance().build(RouterPathHome.TEST_ACTIVITY).navigation()

//            ARouter.getInstance().build(RouterPathClueAllocation.TEST_ACTIVITY).navigation()

//            ARouter.getInstance().build(RouterPathSubmarine.TEST_ACTIVITY).navigation()

//            ARouter.getInstance().build(RouterPathRouterPathRemind.TEST_ACTIVITY).navigation()

        } else {
            try {
                ARouter.getInstance().build(RouterPathLogin.LOGIN_MAIN).navigation()
            } catch (e: Exception) {
                showToast(e.toString())
            }
        }
        finish()
    }
}
