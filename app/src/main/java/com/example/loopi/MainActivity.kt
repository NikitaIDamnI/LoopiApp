package com.example.loopi

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
                            searchScreen = {FakeScreen(route = "searchScreen", color = Color.Red)},
                            addScreen = {FakeScreen(route = "addScreen", color = Color.Green)},
                            profileScreen = {FakeScreen(route = "profileScreen", color = Color.Magenta)},
                            notificationsScreen = {FakeScreen(route = "notificationsScreen", color = Color.Cyan)},
                            filterScreen = {FakeScreen(route = "filterScreen", color = Color.Yellow)},
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


    @Composable
    private fun FakeScreen(modifier: Modifier = Modifier, route: String,color: Color) {
        Box(
            modifier.fillMaxSize().background(color)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = route,
                textAlign = TextAlign.Center
            )

        }

    }

}



