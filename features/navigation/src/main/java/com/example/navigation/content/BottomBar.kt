package com.example.navigation.content

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.models.Screen
import com.example.navigation.NavigationItem
import com.example.uikit.theme.ColorMainGreen
import com.example.uikit.theme.InactiveColor

@Composable
fun NavigationBarApp(
    modifier: Modifier = Modifier,
    currentRoute: String,
    onNavigation: (Screen) -> Unit,
    onClick: (String) -> Unit,
) {
    val navigationItems = NavigationItem.getNavigationItems()

    NavigationBar(
        modifier = modifier
            .height(50.dp),
        containerColor = ColorMainGreen,

        ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = InactiveColor,
                    indicatorColor = ColorMainGreen
                ),
                onClick = {
                    onNavigation(item.screen)
                    onClick(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                }
            )
        }

    }
}




