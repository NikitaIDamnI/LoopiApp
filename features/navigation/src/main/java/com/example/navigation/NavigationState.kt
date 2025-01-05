package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.domain.models.Content
import com.example.domain.models.Screen
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationState(
    val navController: NavHostController,
) {
    private val _stateNavigationScreen = MutableStateFlow<Screen>(Screen.Home)

    fun navigateTo(route: Screen) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
        _stateNavigationScreen.value = route
    }

    fun navigateToContentDetails(content: Content) {
        navController.navigate(Screen.ContentDetails(content))
    }

}

@Composable
fun rememberNavigationState(
    navController: NavHostController? = null,
): NavigationState {
    val controller = navController ?: rememberNavController()
    return remember {NavigationState(controller)}
}
