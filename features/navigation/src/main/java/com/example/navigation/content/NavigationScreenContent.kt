package com.example.navigation.content

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.navigation.NavigationScreenGraph

@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeScreen: @Composable () -> Unit,
    searchScreen: @Composable () -> Unit,
    addScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
    notificationsScreen: @Composable () -> Unit,
    filterScreen: @Composable () -> Unit,
) {
    NavigationScreenContent(
        modifier = modifier,
        navController = navController,
        homeScreen = homeScreen,
        searchScreen = searchScreen,
        addScreen = addScreen,
        profileScreen = profileScreen,
        notificationsScreen = notificationsScreen,
        filterScreen = filterScreen
    )
}

@Composable
private fun NavigationScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeScreen: @Composable () -> Unit,
    searchScreen: @Composable () -> Unit,
    addScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
    notificationsScreen: @Composable () -> Unit,
    filterScreen: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {

        }
    ) { innerPadding ->

        Box {
            innerPadding
            NavigationScreenGraph(
                navController = navController,
                homeScreen = homeScreen,
                searchScreen = searchScreen,
                addScreen = addScreen,
                profileScreen = profileScreen,
                notificationsScreen = notificationsScreen,
                filterScreen = filterScreen
            )
        }
    }


}
