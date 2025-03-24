package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavigationItem(
    val id : Int,
    val title: String,
    val icon: ImageVector,
) {
    object Home : NavigationItem(
        id = 0,
        title = "Home",
        icon = Icons.Default.Home,
    )

    object Search : NavigationItem(
        id = 1,
        title = "Search",
        icon = Icons.Default.Search,
    )

    object Add : NavigationItem(
        id = 2,
        title = "Add",
        icon = Icons.Default.Add,
    )

    object Profile : NavigationItem(
        id = 3,
        title = "Profile",
        icon = Icons.Default.Person,

    )

    object Notifications : NavigationItem(
        id = 4,
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
