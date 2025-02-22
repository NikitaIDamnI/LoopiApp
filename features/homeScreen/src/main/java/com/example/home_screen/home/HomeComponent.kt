package com.example.home_screen.home

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.home_screen.subscriptions.SubscriptionsComponent
import com.example.home_screen.trend.TrendsComponent
import com.example.uikit.home.TabContents
import com.example.uikit.models.ContentUI
import kotlinx.coroutines.flow.StateFlow


interface HomeComponent {

    val stack: Value<ChildStack<*, HomeComponent.Child>>

    val selectedTab: StateFlow<TabContents>

    fun onClickContent(component: ContentUI)

    fun onClickCategoryContent(categoryTab: TabContents)


    sealed interface Child {
        data class Trends(val component: TrendsComponent) : Child
        data class Subscriptions(val component: SubscriptionsComponent) : Child
    }
}
