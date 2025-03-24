package com.example.navigation.navigationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.home_screen.home.HomeComponent
import com.example.uikit.cards.CardLoading
import com.example.uikit.home.generateRandomAspectRatio
import com.vipulasri.aspecto.AspectoGrid
import kotlinx.serialization.Serializable

interface NavigationComponent {

    val stack: Value<ChildStack<*, Child>>

    fun navigateTo(tab: TabConfig)

    @Serializable
    sealed interface TabConfig  {

        @Serializable
        data object Home : TabConfig

        @Serializable
        data object Search : TabConfig

        @Serializable
        data object Add : TabConfig

        @Serializable
        data object Profile : TabConfig

        @Serializable
        data object Notifications : TabConfig

        @Serializable
        data object Filter : TabConfig
    }

    sealed interface Child {
        data class Home(val component: HomeComponent) : Child
        data class Search(val component: StubComponent) : Child
        data class Add(val component: StubComponent) : Child
        data class Profile(val component: StubComponent) : Child
        data class Notifications(val component: StubComponent) : Child
        data class Filter(val component: StubComponent) : Child
    }

}

class StubComponent(
    componentContext: ComponentContext,
    private val title: String,
    val onBack: () -> Unit = {},
) :
    ComponentContext by componentContext {

    @Composable
    fun RenderNotBackPress() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            AspectoGrid(
                modifier = Modifier
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
                    CardLoading(Modifier)
                }

            }

        }
    }


    @Composable
    fun RenderBackPress(
        onBack: () -> Unit,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            IconButton(modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 30.dp), onClick = { onBack() }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
            Text(modifier = Modifier.align(Alignment.Center), text = "$title Screen", fontSize = 24.sp)
        }
    }


}
