package com.example.navigation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value

import com.example.navigation.navigationScreen.DefaultNavigationComponent
import com.example.navigation.navigationScreen.StubComponent
import com.example.uikit.models.ContentUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize


class DefaultRootComponent @AssistedInject constructor(
    private val navigationComponentFactory: DefaultNavigationComponent.Factory,
    //  private val contentDetailsComponentFactory: DefaultContentDetailsComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.NavigationScreen,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config, componentContext: ComponentContext,
    ): RootComponent.Child {
        return when (config) {
            Config.NavigationScreen -> {
                val component = navigationComponentFactory.create(
                    onContentClick = { content ->
                        navigation.push(Config.ContentDetails(content))
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.NavigationScreen(component)
            }

            is Config.ContentDetails -> {
                RootComponent.Child.ContentDetails(
                    StubComponent(
                        componentContext = componentContext,
                        "ContentDetails",
                        onBack = { navigation.pop()})
                )
            }
        }
    }

    sealed interface Config : Parcelable {

        @Parcelize
        data object NavigationScreen : Config

        @Parcelize
        data class ContentDetails(val content: ContentUI) : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultRootComponent
    }
}
