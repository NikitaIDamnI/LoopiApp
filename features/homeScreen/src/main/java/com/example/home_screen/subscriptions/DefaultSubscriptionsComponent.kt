package com.example.home_screen.subscriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DefaultSubscriptionsComponent @AssistedInject constructor(
    @Assisted("componentContext")componentContext: ComponentContext,
    //private val onContentClick: (ContentUI) -> Unit,
):SubscriptionsComponent,ComponentContext by componentContext {

    @Composable
     override fun Render(modifier: Modifier) {

        Box(modifier = modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
            Column {
                Icon(modifier = Modifier.size(30.dp),imageVector = Icons.Filled.Info, contentDescription = "")
                Text(text ="You have no subscriptions yet", fontSize = 30.sp)
            }

        }

    }

    @AssistedFactory
    interface Factory {
        fun create(
           // @Assisted("onContentClick") onContentClick: (ContentUI) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultSubscriptionsComponent
    }

}
