package com.example.navigation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.example.contentdetailsscreen.DetailsContent
import com.example.navigation.content.NavigationScreen

@Composable
fun RootContent(
    component: RootComponent,

) {
    Children(component.stack) {
        when (val instance = it.instance) {
            is RootComponent.Child.ContentDetails -> {
               DetailsContent(instance.component)
            }

            is RootComponent.Child.NavigationScreen -> {
                NavigationScreen(instance.component)
            }
        }
    }


}

