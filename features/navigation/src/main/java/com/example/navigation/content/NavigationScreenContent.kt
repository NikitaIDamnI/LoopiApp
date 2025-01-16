package com.example.navigation.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.domain.models.Screen
import com.example.navigation.NavigationItem
import com.example.navigation.NavigationScreenGraph
import com.example.navigation.rememberNavigationState
import com.example.uikit.theme.MainBackgroundColor

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

    var currentRoute = remember { mutableStateOf<String>(NavigationItem.Home.route)}

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBarApp(
                modifier = modifier,
                currentRoute = currentRoute.value,
                onNavigation = onNavigation,
                onClick = { currentRoute.value = navController.currentDestination?.route ?: "" }
            )
        }
    ) { innerPadding ->
        Box (modifier = Modifier.padding(innerPadding).background(MainBackgroundColor)){
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

@Composable
fun FakeScreen(modifier: Modifier = Modifier, route: String, routeButton: String) {
    Box(modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = route)
        Text(modifier = Modifier.align(Alignment.TopCenter), text = routeButton)

    }

}
