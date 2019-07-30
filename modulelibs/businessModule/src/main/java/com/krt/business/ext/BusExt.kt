package com.krt.business.ext

fun String.toSex() = when (this.trim()) {
    "1" -> "男"
    "2" -> "女"
    else -> "未知"
}