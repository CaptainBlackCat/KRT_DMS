package com.krt.base.ext

import com.krt.frame.app.config.KRT
import kotlin.reflect.jvm.jvmName

/**
 * Created by xbf
 */
inline fun <reified R, T> R.pref(name: String, default: T) =
        PreferenceExt(KRT.getApplicationContext(), name, default, R::class.jvmName)