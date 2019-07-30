package com.krt.base.ext

import android.support.v4.content.ContextCompat
import com.krt.frame.app.config.KRT

fun getColor(color: Int) = ContextCompat.getColor(KRT.getApplicationContext(), color)

fun getDimen(dimen: Int): Int = KRT.getApplicationContext().resources.getDimension(dimen).toInt()

fun getString(dimen: Int): String = KRT.getApplicationContext().resources.getString(dimen)