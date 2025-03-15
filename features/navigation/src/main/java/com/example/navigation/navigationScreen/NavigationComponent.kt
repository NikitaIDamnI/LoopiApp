package com.example.navigation.navigationScreen

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import kotlinx.parcelize.Parcelize

interface NavigationComponent {

    val stack: Value<ChildStack<*, Child>>

    fun navigateTo(tab: TabConfig)

    sealed interface TabConfig : Parcelable {

        @Parcelize
        data object Home : TabConfig

        @Parcelize
        data object Search : TabConfig

        @Parcelize
        data object Add : TabConfig

        @Parcelize
        data object Profile : TabConfig

        @Parcelize
        data object Notifications : TabConfig

        @Parcelize
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
            modifier = Modifier.fillMaxSize(),

            contentAlignment = Alignment.Center
        ) {
            Text(text = "ContentID: $title Screen", fontSize = 24.sp)

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
