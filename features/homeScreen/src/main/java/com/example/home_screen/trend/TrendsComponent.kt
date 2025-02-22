package com.example.home_screen.trend

import com.example.uikit.models.ContentUI
import kotlinx.coroutines.flow.StateFlow

 interface TrendsComponent {
    val model: StateFlow<TrendsScreenStore.State>
    fun onClickContent(сontentUI: ContentUI)
    fun loadNextContent()
}
