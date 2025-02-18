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
import androidx.navigation.NavHostController
import com.example.domain.models.Screen
import com.example.navigation.NavigationItem
import com.example.navigation.NavigationScreenGraph
import com.example.navigation.content.navigationBar.LoopiNavBar
import com.example.navigation.rememberNavigationState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun NavigationScreen(
    homeScreen: @Composable () -> Unit,
    searchScreen: @Composable () -> Unit,
    addScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit,
    notificationsScreen: @Composable () -> Unit,
    filterScreen: @Composable () -> Unit,

    ) {
    val navigationState = rememberNavigationState()
    NavigationScreenContent(
        navController = navigationState.navController,
        homeScreen = homeScreen,
        searchScreen = searchScreen,
        addScreen = addScreen,
        profileScreen = profileScreen,
        notificationsScreen = notificationsScreen,
        filterScreen = filterScreen,
        onNavigation = navigationState::navigateTo
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
    onNavigation: (Screen) -> Unit,
) {

    var currentRoute = remember { mutableStateOf<String>(NavigationItem.Home.route) }
    val hazeState = remember { HazeState() }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .haze(
                    hazeState,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    tint = Color.Black.copy(alpha = .2f),
                    blurRadius = 30.dp,
                )
                .background(Color.Transparent)
        ) {
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

        LoopiNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            hazeState = hazeState,
            currentRoute =  currentRoute ,
            onNavigation = onNavigation,
            onClick = { currentRoute.value = it }
        )
    }
}






