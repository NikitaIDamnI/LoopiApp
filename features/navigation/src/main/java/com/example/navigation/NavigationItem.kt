package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.domain.models.Screen
import kotlinx.serialization.ExperimentalSerializationApi

sealed class NavigationItem(
    val screen: Screen,
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Home : NavigationItem(
        route = getRoute(Screen.Home),
        title = "Home",
        icon = Icons.Default.Home,
        screen = Screen.Home
    )

    object Search : NavigationItem(
        route = getRoute(Screen.Search),
        title = "Search",
        icon = Icons.Default.Search,
        screen = Screen.Search
    )

    object Add : NavigationItem(
        route = getRoute(Screen.AddScreen),
        title = "Add",
        icon = Icons.Default.Add,
        screen = Screen.AddScreen

    )

    object Profile : NavigationItem(
        route = getRoute(Screen.Profile),
        title = "Profile",
        icon = Icons.Default.Person,
        screen = Screen.Profile

    )

    object Notifications : NavigationItem(
        route = getRoute(Screen.Notifications),
        title = "Notifications",
        icon = Icons.Default.Notifications,
        screen = Screen.Notifications
    )


    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        private fun getRoute(screen: Screen): String {
            return when (screen) {
                Screen.AddScreen -> Screen.AddScreen.serializer().descriptor.serialName
                Screen.Home -> Screen.Home.serializer().descriptor.serialName
                Screen.Notifications -> Screen.Notifications.serializer().descriptor.serialName
                Screen.Profile -> Screen.Profile.serializer().descriptor.serialName
                Screen.Search -> Screen.Search.serializer().descriptor.serialName
                else -> throw RuntimeException("Non-existent screen = $screen")
            }
        }

        fun getNavigationItems(): List<NavigationItem> {
            return listOf(Home, Search, Add, Notifications,Profile )
        }
    }
}
