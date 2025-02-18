package com.example.uikit.models

import androidx.compose.runtime.Stable


@Stable
sealed class StateLoading {

    object Loading : StateLoading()
    object Success : StateLoading()
    data class Error(val message: String) : StateLoading()
}
