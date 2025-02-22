package com.example.navigation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.example.navigation.content.NavigationScreen

@Composable
fun RootContent(
    component: RootComponent,

) {
    Children(component.stack) {
        when (val instance = it.instance) {
            is RootComponent.Child.ContentDetails -> {
                instance.component.RenderBackPress(onBack = { instance.component.onBack() })
            }

            is RootComponent.Child.NavigationScreen -> {
                NavigationScreen(instance.component)
            }
        }
    }


}

