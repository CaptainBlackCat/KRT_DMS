package com.krt.base.ext

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.krt.base.R

fun ImageView.loadImage(contentFrom: Any,
                        path: Any,
                        defaultPic: Int? = null,
                        placeholder: Int? = null,
                        centerCrop: Boolean = true,
                        useCache: Boolean = true,
                        enableNullPlaceHolder: Boolean = false) {

    var _placeHolder = placeholder

    if (null == _placeHolder) {
        //TODO
        _placeHolder = when (defaultPic) {
            BANNER_DEFAULT_PIC -> R.mipmap.ic_launcher
            USER_HEAD_DEFAULT_PIC -> R.mipmap.ic_launcher_round
            else -> {
                if (enableNullPlaceHolder) {
                    null
                } else {
                    throw ExceptionInInitializerError("must set a default pic !!!!!!!!!")
                }
            }
        }
    }

    val options = getOptions(_placeHolder, _placeHolder, useCache, centerCrop)
    val requestBuilder = when (contentFrom) {
        is Fragment -> Glide.with(contentFrom).load(path).apply(options)
        is Activity -> Glide.with(contentFrom).load(path).apply(options)
        is View -> Glide.with(contentFrom).load(path).apply(options)
        else -> {
            Glide.with(contentFrom as Context).load(path).apply(options)
        }
    }
    requestBuilder.into(this)
}

/**
 * 取消加载
 */
fun ImageView.loadClear(context: Context) {
    Glide.with(context).clear(this)
}

/**
 * 默认配置
 */
private fun getOptions(placeholder: Int? = null, error: Int? = null,
                       useCache: Boolean,
                       centerCrop: Boolean): RequestOptions {
    val options = RequestOptions()
            .dontAnimate()

    placeholder?.apply {
        options.placeholder(this)
    }

    error?.apply {
        options.error(this)
    }

    if (centerCrop) {
        options.centerCrop()
    }

    if (useCache)
        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    return options
}

const val BANNER_DEFAULT_PIC = 1     //Banner 图片默认图
const val USER_HEAD_DEFAULT_PIC = 4      //用户头像   默认图