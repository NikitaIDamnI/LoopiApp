package com.example.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.contentdetailsscreen.DetailsComponent
import com.example.navigation.navigationScreen.NavigationComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class NavigationScreen (val component: NavigationComponent): Child
        data class ContentDetails (val component: DetailsComponent): Child
    }
}
