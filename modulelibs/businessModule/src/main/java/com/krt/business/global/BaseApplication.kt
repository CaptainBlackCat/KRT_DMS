package com.krt.business.global

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Environment
import android.support.multidex.MultiDex
import com.bumptech.glide.Glide
import com.krt.business.BuildConfig
import com.krt.business.user.UserDefault
import com.krt.component.ModuleServiceFactory
import com.krt.frame.app.config.KRT
import me.yokeyword.fragmentation.Fragmentation

abstract class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        KRT.init(applicationContext,
                initLeakCanary = true,
                actionThreadInit = {
                    ModuleServiceFactory.instance.initModuleAfterApplicationCreate()
                })
                .withJavascriptInterface("CJInterface")
                .withHost("http://172.16.1.72:8111")
//                .withHost("http://192.168.1.55:8111")
                .withBaseFilePath(Environment.getExternalStorageDirectory().absolutePath + "/krt")
                .withCacheFilePath(cacheDir.absolutePath)
//            .withDefaultHttpHeaders(HttpHeaders("Authorization", "xiebingfeng"))
                .withGlideCacheFilePath(cacheDir.absolutePath + "/glideCache")
                .withFragmentSkipAnimTime(300)
                //TODO
//            .withNetworkCustomMade(NetworkCustomMade(300) {
//                showToast("定制返回登入界面")
//                true
//            })
                .configure()

        //先清除用户缓存
        UserDefault.clear()

//        initFragmentJustForDebug()


//        // 分别为MainThread和VM设置Strict Mode
        if (BuildConfig.DEBUG) {

//            initFragmentJustForDebug()

//            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
////                    .detectDiskReads()
////                    .detectDiskWrites()
//                    .detectNetwork()
//                    .detectResourceMismatches()
//                    .detectCustomSlowCalls()
//                    .penaltyFlashScreen()
//                    .penaltyLog()
//                    .penaltyDialog()
//                    .build())
////
//            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects()
//                    .detectLeakedClosableObjects()
//                    .detectLeakedRegistrationObjects()
//                    .detectActivityLeaks()
//                    .penaltyLog()
//                    .build())
        }

        registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {
            }

            override fun onConfigurationChanged(newConfig: Configuration?) {
                Glide.get(applicationContext).clearMemory()
            }
        })

        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
                KRT.getConfigurators().withActivity(activity)
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }
        })
    }

    private fun initFragmentJustForDebug() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                // 开发环境：true时，遇到异常："Can not perform this action after onSaveInstanceState!"时，抛出，并Crash;
                // 生产环境：false时，不抛出，不会Crash，会捕获，可以在handleException()里监听到
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                // 生产环境时，捕获上述异常（避免crash），会捕获
                // 建议在回调处上传下面异常到崩溃监控服务器
                .handleException {
                    // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                    // Bugtags.sendException(e);
                }
                .install()
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}