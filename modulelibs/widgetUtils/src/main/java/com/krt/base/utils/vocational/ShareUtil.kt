//package com.krt.base.utils.vocational
//
//import android.app.Activity
//import android.graphics.Bitmap
//
///**
// * Created by Flying on 2018/7/12.
// */
//object ShareUtil {
//
//    /**
//     * 分享 WebUrl
//     */
//    fun shareWebUrl(activity: Activity, title: String? = null, thumbUrl: String? = null, webUrl: String, shareType: Int, actionSuccess: (() -> Unit)? = null) {
//        val shareAction = getShareAction(activity, shareType)
//
//        if (shareType != SHARE_SINA) {
//            val web = UMWeb(webUrl)
//            web.title = title
//            web.setThumb(UMImage(activity, thumbUrl))
//            web.description = title
//            shareAction.withMedia(web)
//        } else {
//            val image = UMImage(activity, thumbUrl)
//            shareAction.withMedia(image)
//            shareAction.withText(title + webUrl)
//        }
//
//        shareAction.setCallback(object : UMShareListener {
//            override fun onResult(p0: SHARE_MEDIA?) {
//                actionSuccess?.invoke()
//            }
//
//            override fun onCancel(p0: SHARE_MEDIA?) {
//            }
//
//            override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
//            }
//
//            override fun onStart(p0: SHARE_MEDIA?) {
//            }
//        }).share()
//    }
//
//    /**
//     * 分享 Bitmap
//     */
//    fun shareBitmap(activity: Activity, bitmap: Bitmap, shareType: Int, actionSuccess: (() -> Unit)? = null) {
//
//        val thumb = UMImage(activity, bitmap)
//        val image = UMImage(activity, bitmap)
//        image.setThumb(thumb)
//
//        getShareAction(activity, shareType).withMedia(image)
//                .setCallback(object : UMShareListener {
//                    override fun onResult(p0: SHARE_MEDIA?) {
//                        actionSuccess?.invoke()
//                    }
//
//                    override fun onCancel(p0: SHARE_MEDIA?) {
//                    }
//
//                    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
//                    }
//
//                    override fun onStart(p0: SHARE_MEDIA?) {
//                    }
//                }).share()
//    }
//
//    private fun getShareAction(activity: Activity, shareType: Int) = ShareAction(activity)
//            .setPlatform(when (shareType) {
//                SHARE_WEIXIN_CIRCLE -> SHARE_MEDIA.WEIXIN_CIRCLE
//                SHARE_WEIXIN -> SHARE_MEDIA.WEIXIN
//                SHARE_SINA -> SHARE_MEDIA.SINA
//                else -> {
//                    SHARE_MEDIA.SINA
//                }
//            })
//
//    const val SHARE_WEIXIN_CIRCLE = 1
//    const val SHARE_WEIXIN = 2
//    const val SHARE_SINA = 3
//
//}
