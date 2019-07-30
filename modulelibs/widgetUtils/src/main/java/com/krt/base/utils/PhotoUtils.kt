package com.krt.base.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

object PhotoUtils {

    val GET_IMAGE_BY_CAMERA = 5001
    val GET_IMAGE_FROM_PHONE = 5002
    val CROP_IMAGE = 5003
    var imageUriFromCamera: Uri? = null
//    var cropImageUri: Uri

    fun openLocalImage(fragment: Fragment) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        fragment.startActivityForResult(intent, GET_IMAGE_FROM_PHONE)
    }

    fun openCameraImage(fragment: Fragment) {
        imageUriFromCamera = createImagePathUri(fragment.context!!)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
        // 返回图片在onActivityResult中通过以下代码获取
        // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera)
        fragment.startActivityForResult(intent, GET_IMAGE_BY_CAMERA)
    }

    private fun createImagePathUri(context: Context): Uri {
        val timeFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
        val time = System.currentTimeMillis()
        val imageName = timeFormatter.format(Date(time))
        // ContentValues是我们希望这条记录被创建时包含的数据信息
        val values = ContentValues(3)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
        values.put(MediaStore.Images.Media.DATE_TAKEN, time)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        val filePath = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return filePath
    }

}