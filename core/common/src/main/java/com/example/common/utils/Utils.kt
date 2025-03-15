package com.example.common.utils

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

private const val REGEX_PAGE = "[?&]page=(\\d+)"

fun logD(caller: Any, message: String) {
    val tag = caller::class.simpleName ?: "UnknownClass"
    Log.d(tag, message)
}

fun String?. parseNextPage(): Int {
    if (this.isNullOrBlank()) return -1

    val regex = Regex(REGEX_PAGE)
    val matchResult = regex.find(this)

    // Извлекаем значение и преобразуем в Int
    return matchResult?.groups?.get(1)?.value?.toIntOrNull() ?: -1
}

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T>{
    return merge(this,another)
}

fun ComponentContext.componentScope() = CoroutineScope(
    Dispatchers.Main.immediate + SupervisorJob()
).apply {
    lifecycle.doOnDestroy { cancel() }
}
