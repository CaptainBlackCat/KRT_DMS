package com.krt.base.ext

fun String.copy(multiple: Int): String {
    val stringBuilder = StringBuilder()
    for (i in 0..multiple) {
        stringBuilder.append(this)
    }
    return stringBuilder.toString()
}

fun String.toJson(value: Int): String {
    return "{\"" + this + "\":" + value + "}"
}

fun String.toJson(value: String): String {
    return "{\"" + this + "\":\"" + value + "\"" + "}"
}

fun String.getFileName(): String {
    return this.substring(this.lastIndexOf("/") + 1, this.lastIndexOf("."))
}

fun String.Companion.empty() = ""

fun String.removeTheBlankSpace(): String {
    return this.replace(" ", "")
}