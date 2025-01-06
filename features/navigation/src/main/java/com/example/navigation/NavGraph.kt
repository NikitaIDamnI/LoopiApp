package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.domain.models.Content

import com.example.domain.models.Screen
import kotlin.reflect.typeOf


@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    navigationScreen: @Composable () -> Unit,
    authScreen: @Composable () -> Unit,
    contentDetails: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NavigationScreen
    ) {
        composable<Screen.Authorization> {
            authScreen()
        }
        composable<Screen.NavigationScreen> {
            navigationScreen()
        }
        composable<Screen.ContentDetails> (
            typeMap = mapOf(
                typeOf<Content>() to toNavType<Content>()
            )
        ){
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

