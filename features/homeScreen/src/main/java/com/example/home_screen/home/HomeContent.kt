package com.example.home_screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.example.home_screen.home.tab.TabRowCategoryContent
import com.example.home_screen.trend.TrendsContent
import com.example.uikit.home.TabContents
import com.example.uikit.models.ContentUI

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    component: HomeComponent,
    onClickContent: (ContentUI) -> Unit,
    onSetting: () -> Unit,
) {

    var tabHeight =
        remember { mutableStateOf(TabContents.Companion.MAX_HEIGHT.dp) }
    val selectedTab = component.selectedTab.collectAsState()


    val nestedScrollConnection = rememberNestedScrollConnection(
        tabHeightState = { tabHeight.value },
        onTabHeightChange = { tabHeight.value = it }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(nestedScrollConnection)
    ) {
        TabRowCategoryContent(
            modifier = Modifier
                .width(700.dp)
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.CenterHorizontally),
            selectedTab = { selectedTab.value },
            onTabSelected = { component.onClickCategoryContent(it) },
            paddingTab = 50.dp,
            tabHeight = tabHeight,
        )
        Children(component.stack) {
            when (val instance = it.instance) {
                is HomeComponent.Child.Subscriptions -> {
                    instance.component.Render()
                }

                is HomeComponent.Child.Trends -> {
                    TrendsContent(
                        componentContext = instance.component,
                        onClickContent = onClickContent,
                        onSetting = onSetting
                    )
                }
            }
        }


    }

}



@Composable
private fun rememberNestedScrollConnection(
    tabHeightState: () -> Dp,
    onTabHeightChange: (Dp) -> Unit,
): NestedScrollConnection {
    return remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val currentHeight = tabHeightState()

                val newHeight = when {
                    delta > 0 -> min(
                        TabContents.Companion.MAX_HEIGHT.dp,
                        currentHeight + ((delta / 10).dp)
                    )

                    delta < 0 -> max(
                        TabContents.Companion.MIN_HEIGHT.dp,
                        currentHeight + ((delta / 10).dp)
                    )

                    else -> currentHeight
                }

                if (newHeight != currentHeight) {
                    onTabHeightChange(newHeight)
                }

                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                val currentHeight = tabHeightState()

                val finalHeight =
                    if (currentHeight >= (TabContents.Companion.MAX_HEIGHT / 2).dp) {
                        TabContents.Companion.MAX_HEIGHT.dp
                    } else {
                        TabContents.Companion.MIN_HEIGHT.dp
                    }

                if (finalHeight != currentHeight) {
                    onTabHeightChange(finalHeight)
                }

                return super.onPreFling(available)
            }
        }
    }
}
