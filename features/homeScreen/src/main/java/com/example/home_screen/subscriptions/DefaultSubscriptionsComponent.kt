package com.example.home_screen.subscriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.example.uikit.cards.CardLoading
import com.example.uikit.home.generateRandomAspectRatio
import com.vipulasri.aspecto.AspectoGrid
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DefaultSubscriptionsComponent @AssistedInject constructor(
    @Assisted("componentContext") componentContext: ComponentContext,
    //private val onContentClick: (ContentUI) -> Unit,
) : SubscriptionsComponent, ComponentContext by componentContext {

    @Composable
    override fun Render(modifier: Modifier) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            AspectoGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp),
                maxRowHeight = 300.dp,
                itemPadding = PaddingValues(
                    horizontal = 5.dp,
                    vertical = 4.dp
                ),
            )  {
//                Icon(
//                    modifier = Modifier.size(30.dp),
//                    imageVector = Icons.Filled.Info,
//                    contentDescription = ""
//                )
//                Text(text = "You have no subscriptions yet", fontSize = 30.sp)

                items(
                    items = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                    key = { it },
                    aspectRatio = { generateRandomAspectRatio() }
                ) {
                    CardLoading(modifier)
                }

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
