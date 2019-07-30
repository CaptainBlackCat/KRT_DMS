package com.krt.base.ext

import com.krt.base.utils.TimeUtils
import java.text.NumberFormat

/**
 * precision:小数点个数
 * percentMode:返回是否带百分比符号
 */
fun Double.toPrecision(precision: Int = 2, percentMode: Boolean = false): String {
    return getFormatResult(this, precision, percentMode)
}

fun Float.toPrecision(precision: Int = 2, percentMode: Boolean = false): String {
    return getFormatResult(this, precision, percentMode)
}

fun Float.toRMB(isSpaceAdd: Boolean = true): String {
    val stringBuilder = StringBuilder("¥")
    if (isSpaceAdd) {
        stringBuilder.append(" ")
    }

    if (this > 1) {
        stringBuilder.append(getFormatResult(this, 2, false))
    } else {
        stringBuilder.append(getFormatResult(this, 5, false))
    }
    return stringBuilder.toString()
}

fun Float.toDollar(isSpaceAdd: Boolean = true): String {
    val stringBuilder = StringBuilder("\$")
    if (isSpaceAdd) {
        stringBuilder.append(" ")
    }

    if (this > 1) {
        stringBuilder.append(getFormatResult(this, 2, false))
    } else {
        stringBuilder.append(getFormatResult(this, 5, false))
    }
    return stringBuilder.toString()
}


fun Double.toRMB(isSpaceAdd: Boolean = true): String {
    val stringBuilder = StringBuilder("¥")
    if (isSpaceAdd) {
        stringBuilder.append(" ")
    }

    if (this > 1) {
        stringBuilder.append(getFormatResult(this, 2, false))
    } else {
        stringBuilder.append(getFormatResult(this, 5, false))
    }
    return stringBuilder.toString()
}

fun Double.toDollar(isSpaceAdd: Boolean = true): String {
    val stringBuilder = StringBuilder("\$")
    if (isSpaceAdd) {
        stringBuilder.append(" ")
    }

    if (this > 1) {
        stringBuilder.append(getFormatResult(this, 2, false))
    } else {
        stringBuilder.append(getFormatResult(this, 5, false))
    }
    return stringBuilder.toString()
}

fun Double.toQuoteChange(): String {
    return if (this > 0) {
        "+" + String.format("%.2f", this) + "%"
    } else {
        String.format("%.2f", this) + "%"
    }
}

private fun getFormatResult(any: Any, precision: Int, percent: Boolean): String {
    val numberFormat: NumberFormat = if (percent)
        NumberFormat.getPercentInstance()
    else
        NumberFormat.getNumberInstance()

    numberFormat.maximumFractionDigits = precision
    return numberFormat.format(any)
}


fun Int.toChinese() = when (this) {
    1 -> "一"
    2 -> "二"
    3 -> "三"
    4 -> "四"
    5 -> "五"
    6 -> "六"
    7 -> "七"
    else -> {
        "不知道"
    }
}

fun Int.toMessageCount() = if (this > 99) 99 else this


fun Long.toCustomTime(): String {
    val currentTime = System.currentTimeMillis()
    val minuteScale = (currentTime - this) / (60 * 1000)

    when (currentTime - this) {
        in 0..(1 * 60 * 1000) -> {
            return "刚刚"
        }
        in 1 * 60 * 1000..(60 * 60 * 1000) -> {
            return minuteScale.toString() + "分钟前"
        }
        in 60 * 60 * 1000..(2 * 60 * 60 * 1000) -> {
            val minute = (currentTime - this - 60 * 60 * 1000) / (60 * 1000)
            return "1小时" + if (minute == 0L) "" else (minute.toString() + "分钟前")
        }
        else -> {
            return when {
                TimeUtils.isToday(this) -> TimeUtils.formatHourAndMinute(this)
                TimeUtils.isTheSameYear(this, currentTime) -> TimeUtils.formatMonthAndDay(this)
                else -> TimeUtils.formatYearMonthAndDay(this)
            }
        }
    }
}

fun Int.Companion.empty() = -1
