package com.krt.business.update

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatDialog
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.blankj.utilcode.util.AppUtils
import com.krt.base.ext.getFileName
import com.krt.base.ext.getString
import com.krt.business.R
import com.krt.business.update.bean.UpdateEntityDTO
import com.krt.frame.app.config.ConfigKeys
import com.krt.frame.app.config.KRT
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.frame.permission.PermissionDelegate
import com.lzy.okgo.OkGo
import com.lzy.okgo.db.DownloadManager
import com.lzy.okgo.model.Progress
import com.lzy.okserver.OkDownload
import com.lzy.okserver.download.DownloadListener
import com.lzy.okserver.download.DownloadTask
import kotlinx.android.synthetic.main.bus_update_app_dialog.*
import me.yokeyword.fragmentation.SupportActivity
import java.io.File


/**
 * 更新APP Dialog
 */
class UpdateAppDialog(
        private val mSupportActivity: SupportActivity?,
        val entity: UpdateEntityDTO,
        private val updateType: Int
) : AppCompatDialog(mSupportActivity) {

    private var mDownloadTask: DownloadTask? = null

    private val mDownloadFileDir = KRT.getConfigurators().getConfiguration<String>(ConfigKeys.BASE_FILE_PATH) + "/app/"

    private lateinit var mFileName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bus_update_app_dialog)
        setCancelable(false)

        val window = window
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        initView()
        updateData()
    }

    private fun initView() {
        update_process.max = 100

        update_not_update.setOnClickListener {
            dismiss()
        }

        update_button.setOnClickListener {
            mSupportActivity?.let {
                PermissionDelegate.Factory().create(it).checkStoragePermission {
                    when (update_button.text) {
                        context.getString(R.string.bus_update_right_now),
                        context.getString(R.string.bus_continue_to_update) -> {
                            startDownload()
                        }
                        context.getString(R.string.bus_installation) -> {
                            installApk()
                        }
                    }
                }
            }
        }

        if (updateType == APP_UPDATE_MUST) {
            update_not_update.visibility = View.GONE
        }
    }

    private fun updateData() {
        update_title.text = entity.title
        update_content.text = entity.content

        mFileName = entity.downloadUrl.getFileName() + "_" + entity.versionCode + ".apk"

        DownloadManager.getInstance().get(mFileName)?.apply {
            mDownloadTask = OkDownload.restore(this)//
                    .register(InnerDownloadListener())//

            val filePath = File(mDownloadTask?.progress?.filePath)
            if (!filePath.exists()) {
                mDownloadTask?.remove()
                mDownloadTask = null
                return
            }


            refreshUi(this)
            if (fraction >= 1) {
                update_button.text = context.getString(R.string.bus_installation)
            } else {
                update_button.text = context.getString(R.string.bus_continue_to_update)
            }
        }
    }

    /**
     * 开始下载
     */
    private fun startDownload() {
        OkDownload.getInstance().folder = mDownloadFileDir

        if (mDownloadTask == null) {
            val request = OkGo.get<File>(entity.downloadUrl)//
            mDownloadTask = OkDownload.request(mFileName, request)//
                    .fileName(getString(R.string.app_name).toLowerCase() + entity.versionCode)
                    .fileName(mFileName)
                    .save()//
                    .register(InnerDownloadListener())
        }

        mDownloadTask?.start()
    }

    private fun refreshUi(progress: Progress?) {
        progress?.let {
            update_process.progress = (it.fraction * 100).toInt()
        }
    }

    private fun installApk() {
        val file = File(mDownloadTask?.progress?.filePath)

        AppUtils.installApp(file)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        mDownloadTask?.apply {
            unRegister(DOWNLOAD_LISTENER)
            pause()
            mDownloadTask = null
        }
    }

    inner class InnerDownloadListener : DownloadListener("DownloadListener") {
        override fun onFinish(t: File?, progress: Progress?) {
            update_button.text = context.getString(R.string.bus_installation)
            update_button.isEnabled = true

            installApk()
        }

        override fun onRemove(progress: Progress?) {
        }

        override fun onProgress(progress: Progress?) {
            refreshUi(progress)
        }

        override fun onError(progress: Progress?) {
        }

        override fun onStart(progress: Progress?) {
            update_process.progress = 0
            update_button.isEnabled = false
            update_button.text = context.getString(R.string.bus_is_downloading)
        }
    }

    companion object {

        fun checkVersion(
                viewModel: BaseViewModel,
                checkFromAppUp: Boolean,
                mustUpdate: Boolean = false,
                actionError: (() -> Unit)? = null,
                actionComplete: (() -> Unit)? = null
        ) {
            var updateType = 0
            var entity: UpdateEntityDTO? = null

//            OkGo.get<String>(KRT.getConfiguration(ConfigKeys.CHECK_APP_UPDATE) as? String)//
//                .converter(StringConvert())
//                .adapt<Observable<String>>(ObservableBody())//
//                .subscribeOn(Schedulers.io())//
//                .observeOn(Schedulers.io())
//                .doOnNext {
//                    entity = JSONObject.parseObject(it, UpdateEntityDTO::class.java)
//                    if (entity == null) {
//                        return@doOnNext
//                    }
//
//                    if (!entity!!.appUsable) {
//                        updateType = APP_UPDATE_UN_USABLE
//                        return@doOnNext
//                    }
//
//                    val currentVersion = AppUtils.getAppVersionName().split(".")
//                    val updateVersion = entity!!.versionCode.split(".")
//
//                    if (updateVersion[0] > currentVersion[0] || updateVersion[1] > currentVersion[1]) {
//                        updateType = APP_UPDATE_MUST
//                    } else if (updateVersion[2] > currentVersion[2]) {
//                        updateType = APP_UPDATE_MAY_NOT
//
//                        if (checkFromAppUp) {
//                            val lastCheckUpdateTime =
//                                SPUtils.getInstance().getLong(CHECK_APP_UPDATE_TIME)
//                            val lastCheckUpdateVersion =
//                                SPUtils.getInstance().getString(CHECK_APP_UPDATE_VERSION)
//
//                            if (lastCheckUpdateTime > 0 && TimeUtils.isToday(lastCheckUpdateTime)) {
//
//                                //哪怕是今天检查过更新的，但最后一次版本号发生了变化还是要更新
//                                updateType = if (lastCheckUpdateVersion != entity!!.versionCode) {
//                                    APP_UPDATE_MAY_NOT
//                                } else {
//                                    APP_UPDATE_NOT
//                                }
//                            }
//                        }
//                        SPUtils.getInstance().put(CHECK_APP_UPDATE_TIME, System.currentTimeMillis())
//                        SPUtils.getInstance().put(CHECK_APP_UPDATE_VERSION, entity!!.versionCode)
//                    }
//                }
//                .observeOn(AndroidSchedulers.mainThread())//
//                .subscribe(object : Observer<String> {
//                    override fun onSubscribe(@NonNull d: Disposable) {
//                        viewModel.addDisposable(d)
//                    }
//
//                    override fun onNext(@NonNull serverModel: String) {
//                        if (updateType == APP_UPDATE_UN_USABLE) {
//                            KRT.getCurrentActivity()?.let {
//                                MaterialDialog(it)
//                                    .cancelOnTouchOutside(false)
//                                    .title(R.string.base_warn)
//                                    .message(R.string.bus_the_background_is_under_maintenance)
//                                    .positiveButton(R.string.base_sure, click = {
//                                        AppManager.finishAllActivity()
//                                    })
//                                    .show()
//                            }
//                            return
//                        }
//
//                        //必须要更新，从DEBUG界面过来的，专门用来更新当前版本
//                        if (mustUpdate) {
//                            updateType = APP_UPDATE_MAY_NOT
//                        }
//
//                        if (updateType > 0) {
//                            UpdateAppDialog(KRT.getCurrentActivity() as? BaseActivity, entity!!, updateType).show()
//                        } else {
//                            if (!checkFromAppUp) {
//                                KRT.getCurrentActivity()?.let {
//                                    MaterialDialog(it)
//                                        .title(R.string.base_warn)
//                                        .message(R.string.bus_current_version_is_the_last_version)
//                                        .positiveButton(R.string.base_sure, click = {
//                                        })
//                                        .show()
//                                }
//                            }
//                        }
//                    }
//
//                    override fun onError(@NonNull e: Throwable) {
//                        actionError?.invoke()
//                    }
//
//                    override fun onComplete() {
//                        actionComplete?.invoke()
//                    }
//                })
        }

        private const val APP_UPDATE_NOT = 0  //不用更新

        private const val APP_UPDATE_MAY_NOT = 1   //可更新

        private const val APP_UPDATE_MUST = 2   //必须更新

        const val APP_UPDATE_UN_USABLE = 3    //当前APP不可用

        const val DOWNLOAD_LISTENER: String = "DownloadListener"

        private const val CHECK_APP_UPDATE_TIME: String = "check_app_update_time"
        private const val CHECK_APP_UPDATE_VERSION: String = "check_app_update_version"
    }
}
