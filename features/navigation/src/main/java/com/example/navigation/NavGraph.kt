package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.domain.models.NavigationType
import com.example.domain.models.Screen
import com.example.domain.models.Screen.ContentDetails

//object Authorization : Screen()
//object Home : Screen()
//object Search : Screen()
//object AddScreen : Screen()
//object Profile : Screen()
//object Notifications : Screen()
//object Filter : Screen()
//data class ContentDetails(val content: Content) : Screen()

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    navigationScreen: @Composable (NavHostController) -> Unit,
    authScreen: @Composable () -> Unit,
    contentDetails: @Composable () -> Unit,
    startDestination: NavigationType = Screen.Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<NavigationType.AuthNavigation> {
            authScreen()
        }
        composable<NavigationType.MainNavigation> {
            navigationScreen(navController)
        }
        composable<NavigationType.ContentDetailsNavigation> {
            contentDetails()
        }
    }
}

@Composable
fun NavigationScreenGraph(
    navController: NavHostController,
    homeScreen: @Composable () -> Unit,
    searchScreen: @Composable () -> Unit,
    addScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
    notificationsScreen: @Composable () -> Unit,
    filterScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            homeScreen()
        }
        composable<Screen.Search> {
            searchScreen()
        }
        composable<Screen.AddScreen> {
            addScreen()
        }
        composable<Screen.Profile> {
            profileScreen()
        }
        composable<Screen.Notifications> {
            notificationsScreen()
        }
        composable<Screen.Filter> {
            filterScreen()
        }
    }
}

