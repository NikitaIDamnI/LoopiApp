package com.example.loopi

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.authscreen.ui.AuthScreenContent
import com.example.home_screen.content.HomeScreen
import com.example.navigation.AppNavigationGraph
import com.example.navigation.content.NavigationScreen
import com.example.navigation.rememberNavigationState
import com.example.uikit.theme.LoopiTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setStatusBarIconsColor()
        enableEdgeToEdge()
        setContent {
            LoopiTheme {
                val navigationState = rememberNavigationState()
                AppNavigationGraph(
                    navController = navigationState.navController,
                    navigationScreen = {
                        NavigationScreen(
                            homeScreen = {
                                HomeScreen(
                                    modifier = Modifier,
                                    onClickContent = {},
                                    onSetting = {}
                                )
                            },
                            searchScreen = {},
                            addScreen = {},
                            profileScreen = {},
                            notificationsScreen = {},
                            filterScreen = {},
                        )
                    },
                    authScreen = {
                        AuthScreenContent(
                            modifier = Modifier,
                            onLoginRequest = {},
                            onGuestRequest = {},
                        )
                    },
                    contentDetails = {},
                )


            }

        }
    }

    private fun setStatusBarIconsColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            controller?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            // Альтернативное решение для более старых версий Android
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }
}



