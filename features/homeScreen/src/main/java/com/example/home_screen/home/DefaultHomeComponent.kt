package com.example.home_screen.home

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.home_screen.subscriptions.DefaultSubscriptionsComponent
import com.example.home_screen.trend.DefaultTrendsComponent
import com.example.uikit.home.TabContents
import com.example.uikit.models.ContentUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.parcelize.Parcelize

class DefaultHomeComponent @AssistedInject constructor(
    private val trendsComponentFactory: DefaultTrendsComponent.Factory,
    private val subscriptionsComponentFactory: DefaultSubscriptionsComponent.Factory,
    @Assisted("onContentClick") private val onContentClick: (ContentUI) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : HomeComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<Config, HomeComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Trends,
            childFactory = ::child
        )

    private val _selectedTab: MutableStateFlow<TabContents> = MutableStateFlow(TabContents.TRENDS)
    override val selectedTab: StateFlow<TabContents>
        get() = _selectedTab.asStateFlow()

    override fun onClickContent(component: ContentUI) = onContentClick(component)
    override fun onClickCategoryContent(categoryTab: TabContents) {
       when(categoryTab){
           TabContents.SUBSCRIPTIONS -> {
               navigation.bringToFront(Config.Subscriptions)
               _selectedTab.value = TabContents.SUBSCRIPTIONS
           }
           TabContents.TRENDS -> {
               navigation.bringToFront(Config.Trends)
               _selectedTab.value = TabContents.TRENDS
           }
       }
    }

    private fun child(
        config: Config, componentContext: ComponentContext,
    ): HomeComponent.Child {
        return when (config) {
            Config.Subscriptions -> {
                val component = subscriptionsComponentFactory.create(
                    componentContext = componentContext
                )
                HomeComponent.Child.Subscriptions(component)
            }

            Config.Trends -> {
                val component = trendsComponentFactory.create(
                    onContentClick = onContentClick,
                    componentContext = componentContext
                )
                HomeComponent.Child.Trends(component)
            }
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object Trends : Config

        @Parcelize
        data object Subscriptions : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onContentClick")  onContentClick: (ContentUI) -> Unit,
            ): DefaultHomeComponent
    }
}

