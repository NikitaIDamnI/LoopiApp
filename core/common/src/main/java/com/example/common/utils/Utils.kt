package com.example.common.utils

import android.util.Log

fun logD(caller: Any, message: String) {
    val tag = caller::class.simpleName ?: "UnknownClass"
    Log.d(tag, message)
}
