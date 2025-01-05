package com.example.domain.models

import com.example.domain.models.NavigationType.AuthNavigation
import com.example.domain.models.NavigationType.ContentDetailsNavigation
import com.example.domain.models.NavigationType.MainNavigation

sealed class Screen : NavigationType {
    object Authorization : Screen(),AuthNavigation
    object Home : Screen(), MainNavigation
    object Search : Screen(), MainNavigation
    object AddScreen : Screen(), MainNavigation
    object Profile : Screen(), MainNavigation
    object Notifications : Screen(), MainNavigation
    object Filter : Screen(), MainNavigation
    data class ContentDetails(val content: Content) : Screen(), ContentDetailsNavigation
}

sealed interface NavigationType {
    interface MainNavigation
    interface ContentDetailsNavigation
    interface AuthNavigation
}
