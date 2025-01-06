package com.example.domain.models


import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf


@Serializable
sealed class Screen {
    @Serializable
    object Authorization : Screen()

    @Serializable
    object NavigationScreen : Screen()

    @Serializable
    object Home : Screen()

    @Serializable
    object Search : Screen()

    @Serializable
    object AddScreen : Screen()

    @Serializable
    object Profile : Screen()

    @Serializable
    object Notifications : Screen()

    @Serializable
    object Filter : Screen()

    @Serializable
    object ContentDetails : Screen() //val content: Content
}





