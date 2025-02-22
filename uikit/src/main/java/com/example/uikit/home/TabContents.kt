package com.example.uikit.home

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize


enum class TabContents(val nameTab: String, val index: Int) {
    TRENDS("Trends",0),
    SUBSCRIPTIONS("Subscriptions",1);

    companion object {
        const val MAX_HEIGHT = 80
        const val MIN_HEIGHT = 0
    }


}
