package com.example.navigation.root

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.common.utils.ANIMATION_SPEED
import com.example.contentdetailsscreen.DetailsContent
import com.example.navigation.content.NavigationScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(
    component: RootComponent,
) {
    Children(
        stack = component.stack,
        animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            fallbackAnimation = stackAnimation(slide(animationSpec = tween(ANIMATION_SPEED))),
            selector = { backEvent, _, _ ->
                androidPredictiveBackAnimatable(
                    initialBackEvent = backEvent,
                )
            },
            onBack = component::onBackClicked,
        )
    ) {
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

