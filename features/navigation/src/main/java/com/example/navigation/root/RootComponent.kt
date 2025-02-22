package com.example.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

import com.example.navigation.navigationScreen.NavigationComponent
import com.example.navigation.navigationScreen.StubComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class NavigationScreen (val component: NavigationComponent): Child
        data class ContentDetails (val component: StubComponent): Child
    }
}
