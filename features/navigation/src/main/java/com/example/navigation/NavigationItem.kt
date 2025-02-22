package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavigationItem(

    val title: String,
    val icon: ImageVector,
) {
    object Home : NavigationItem(
        title = "Home",
        icon = Icons.Default.Home,
    )

    object Search : NavigationItem(
        title = "Search",
        icon = Icons.Default.Search,
    )

    object Add : NavigationItem(
        title = "Add",
        icon = Icons.Default.Add,
    )

    object Profile : NavigationItem(
        title = "Profile",
        icon = Icons.Default.Person,

    )

    object Notifications : NavigationItem(
        title = "Notifications",
        icon = Icons.Default.Notifications,
    )


    companion object {

        fun getNavigationItems(): List<NavigationItem> {
            return listOf(
                Home,
                Search,
                Add,
                Profile,
                Notifications
            )
        }
    }
}
