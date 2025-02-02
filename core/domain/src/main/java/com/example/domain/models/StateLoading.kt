package com.example.domain.models



sealed class StateLoading {

    object Loading : StateLoading()
    object Success : StateLoading()
    data class Error(val message: String) : StateLoading()
}
