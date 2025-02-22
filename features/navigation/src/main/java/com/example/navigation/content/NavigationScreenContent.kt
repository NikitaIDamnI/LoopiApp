@file:Suppress("LongParameterList")

package com.example.navigation.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.example.home_screen.home.HomeContent
import com.example.navigation.NavigationItem

import com.example.navigation.content.navigationBar.LoopiNavBar
import com.example.navigation.navigationScreen.NavigationComponent
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun NavigationScreen(component: NavigationComponent) {
    val hazeState = remember { HazeState() }
    val currentNavigationItem = remember { mutableStateOf<NavigationItem>(NavigationItem.Home) }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        // Главный контейнер с размытой областью
        Box(
            modifier = Modifier
                .haze(
                    hazeState,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    tint = Color.Black.copy(alpha = .2f),
                    blurRadius = 30.dp
                )
                .background(Color.Transparent)
        ) {
            // Здесь отображаются экраны
            Children(stack = component.stack) { child ->
                when (val instance = child.instance) {
                    is NavigationComponent.Child.Home -> {
                        HomeContent(
                            component= instance.component,
                            onClickContent = { instance.component.onClickContent(it) },
                            onSetting = {},
                        )
                        currentNavigationItem.value = NavigationItem.Home
                    }

                    is NavigationComponent.Child.Search -> {
                        instance.component.RenderNotBackPress()
                        currentNavigationItem.value = NavigationItem.Search
                    }

                    is NavigationComponent.Child.Add -> {
                        instance.component.RenderNotBackPress()
                        currentNavigationItem.value = NavigationItem.Add
                    }

                    is NavigationComponent.Child.Profile -> {
                        instance.component.RenderNotBackPress()
                        currentNavigationItem.value = NavigationItem.Profile
                    }

                    is NavigationComponent.Child.Notifications -> {
                        instance.component.RenderNotBackPress()
                        currentNavigationItem.value = NavigationItem.Notifications
                    }

                    is NavigationComponent.Child.Filter -> {

                    }
                }
            }
        }

        // Нижняя навигационная панель
        LoopiNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            hazeState = hazeState,
            currentRoute = currentNavigationItem,
            onNavigation = { onNavigation(it, component) },

            )
    }
}


private fun onNavigation(item: NavigationItem, component: NavigationComponent) {
    when (item) {
        NavigationItem.Add -> {
            component.navigateTo(NavigationComponent.TabConfig.Add)
        }

        NavigationItem.Home -> {
            component.navigateTo(NavigationComponent.TabConfig.Home)
        }

        NavigationItem.Notifications -> {
            component.navigateTo(NavigationComponent.TabConfig.Notifications)
        }

        NavigationItem.Profile -> {
            component.navigateTo(NavigationComponent.TabConfig.Profile)
        }

        NavigationItem.Search -> {
            component.navigateTo(NavigationComponent.TabConfig.Search)
        }
    }
}





