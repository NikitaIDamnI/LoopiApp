package com.example.loopi

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.authscreen.ui.AuthScreenContent
import com.example.domain.models.Screen
import com.example.home_screen.content.HomeScreen
import com.example.navigation.AppNavigationGraph
import com.example.navigation.content.NavigationScreen
import com.example.navigation.rememberNavigationState
import com.example.uikit.theme.LoopiTheme
import com.vipulasri.aspecto.AspectoGrid
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
           // AspectoGridPreview()
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



