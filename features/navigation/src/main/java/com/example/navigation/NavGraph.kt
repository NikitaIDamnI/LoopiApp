package com.example.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    // Определяем порядок экранов
    val screens = listOf(
        Screen.Home,
        Screen.Search,
        Screen.AddScreen,
        Screen.Profile,
        Screen.Notifications,
    )


    // Запоминаем предыдущий экран
    var previousScreen = remember { mutableStateOf<Screen?>(null) }

    val currentScreen = remember(navController.currentBackStackEntry) {
        navController.currentBackStackEntry?.destination?.route?.let { route ->
            screens.find { it::class.simpleName == route }
        }
    }

    // Определяем направление анимации (правильный isForward)
    val isForward = remember(previousScreen, currentScreen) {
        val previousIndex = screens.indexOf(previousScreen.value)
        val currentIndex = screens.indexOf(currentScreen)
        val forward = currentIndex > previousIndex
        previousScreen.value = currentScreen // Обновляем previousScreen **после вычисления**
        forward
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(700),
                initialOffsetX = { if (isForward) it else -it } // Свайп влево или вправо
            )
        },
        exitTransition = {
            slideOutHorizontally(
                animationSpec = tween(700),
                targetOffsetX = { if (isForward) -it else it } // Свайп влево или вправо
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                animationSpec = tween(700),
                initialOffsetX = { if (isForward) it else -it }
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                animationSpec = tween(700),
                targetOffsetX = { if (isForward) -it else it }
            )
        }
    ) {
        composable<Screen.Home>(
            enterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { it }) }
        ) { homeScreen() }
        composable<Screen.Search>(
            enterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { it }) }
        ) { searchScreen() }
        composable<Screen.AddScreen> (
            enterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { it }) }
        ){ addScreen() }
        composable<Screen.Profile>(
            enterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { it }) }
        ) { profileScreen() }
        composable<Screen.Notifications>(
            enterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(animationSpec = tween(700), initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { it }) }
        ) { notificationsScreen() }
        composable<Screen.Filter> { filterScreen() }
    }
}

