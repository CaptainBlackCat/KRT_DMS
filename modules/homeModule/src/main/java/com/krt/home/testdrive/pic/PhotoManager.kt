package com.krt.home.testdrive.pic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityCompat
import com.krt.base.dialog.ChooseImageDialog
import com.krt.base.utils.PhotoUtils
import com.krt.frame.ext.showToast
import com.krt.home.R
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import me.yokeyword.fragmentation.SupportFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PhotoManager(val fragment: SupportFragment, val action: (File) -> Unit) {

    fun show() {
        ChooseImageDialog(fragment.context!!, fragment).show()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PhotoUtils.GET_IMAGE_FROM_PHONE ->
                if (resultCode == Activity.RESULT_OK) {
                    //                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    initUCrop(data?.data)
                }
            PhotoUtils.GET_IMAGE_BY_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    PhotoUtils.imageUriFromCamera?.let {
                        initUCrop(it)
                    }
                }
            }
            UCrop.REQUEST_CROP -> {
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri = UCrop.getOutput(data!!)

                    resultUri?.apply {
                        val imageFile = File(this.path)
                        if (imageFile.exists()) {
//                            viewModel.uploadHeadUrl(imageFile)

                            action.invoke(imageFile)
                        } else {
                            showToast("图片不存在!")
                        }
                    }
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    val cropError = UCrop.getError(data!!)
                    showToast(cropError?.toString()!!)
                }
            }
        }
    }

    /**
     * 裁剪图片
     */
    private fun initUCrop(uri: Uri?) {
        val timeFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
        val time = System.currentTimeMillis()
        val imageName = timeFormatter.format(Date(time))

//        val destinationUri = Uri.fromFile(File(NECJ.getConfigurators().getConfiguration(ConfigKeys.CACHE_FILE_PATH) as String, "$imageName.jpeg"))

        val destinationUri = Uri.fromFile(File(fragment.activity!!.cacheDir, "$imageName.jpeg"))

        val options = UCrop.Options()
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL)
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(fragment.activity!!, R.color.base_red))
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(fragment.activity!!, R.color.base_red))

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5f)
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666)
        //设置裁剪窗口是否为椭圆
        //options.setCircleDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        options.setCropGridColumnCount(0);
        //设置横线的数量
        options.setCropGridRowCount(0);

        uri?.let {
            UCrop.of(it, destinationUri)
                    .withAspectRatio(1f, 1f)
                    .withMaxResultSize(500, 500)
                    .withOptions(options)
                    .start(fragment.activity!!, fragment)
        }
    }
}