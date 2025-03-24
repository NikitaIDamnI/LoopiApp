package com.example.navigation.navigationScreen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.home_screen.home.DefaultHomeComponent
import com.example.navigation.navigationScreen.NavigationComponent.TabConfig
import com.example.uikit.models.ContentUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DefaultNavigationComponent @AssistedInject constructor(
    private val homeComponentFactory: DefaultHomeComponent.Factory,
    @Assisted("onContentClick") private val onContentClick: (ContentUI) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : NavigationComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<TabConfig>()

    override val stack: Value<ChildStack<*, NavigationComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = TabConfig.Home,
        serializer = TabConfig.serializer(),
        childFactory = ::child
    )

    override fun navigateTo(tab: TabConfig) {
        navigation.bringToFront(tab)
    }

    private fun child(
        config: TabConfig, componentContext: ComponentContext,
    ): NavigationComponent.Child {
        return when (config) {
            TabConfig.Home -> {
                val component = homeComponentFactory.create(
                    onContentClick = onContentClick,
                    componentContext = componentContext
                )
                NavigationComponent.Child.Home(component)
            }

            TabConfig.Search -> NavigationComponent.Child.Search(
                StubComponent(
                    componentContext = componentContext,
                    title = "Search"
                )
            )

            TabConfig.Add -> NavigationComponent.Child.Add(
                StubComponent(
                    componentContext = componentContext,
                    title = "Add"
                )
            )

            TabConfig.Profile -> NavigationComponent.Child.Profile(
                StubComponent(
                    componentContext = componentContext,
                    title = "Profile"
                )
            )

            TabConfig.Notifications -> NavigationComponent.Child.Notifications(
                StubComponent(
                    componentContext = componentContext,
                    title = "Notifications"
                )
            )

            TabConfig.Filter -> NavigationComponent.Child.Filter(
                StubComponent(
                    componentContext = componentContext,
                    title = "Filter"
                )
            )
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onContentClick") onContentClick: (ContentUI) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultNavigationComponent
    }
}

