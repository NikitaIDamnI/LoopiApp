package com.example.home_screen.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.example.home_screen.trend.TrendsContent
import com.example.uikit.home.TabContents
import com.example.uikit.models.ContentUI
import com.example.uikit.theme.InactiveColor

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
            .nestedScroll(nestedScrollConnection)
    ) {
        TabRowWithTabs(
            modifier = Modifier
                .width(700.dp)
                .align(Alignment.CenterHorizontally),
            selectedTab = { selectedTab.value },
            onTabSelected = { component.onClickCategoryContent(it) },
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
private fun TabRowWithTabs(
    modifier: Modifier = Modifier,
    selectedTab: () -> TabContents,
    onTabSelected: (TabContents) -> Unit,
    tabHeight: State<Dp>,
) {

    val selectedTab = selectedTab()

    TabRow(
        modifier = modifier
            .height(tabHeight.value),
        selectedTabIndex = selectedTab.index,
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.background,
        indicator = { tabPositions ->
            // Если индикатор зависит от выбранной вкладки, то его можно оставить здесь
            tabPositions.forEachIndexed { index, tabPosition ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.Companion
                        .tabIndicatorOffset(tabPosition)
                        .background(MaterialTheme.colorScheme.background),
                    color = if (index == selectedTab.index) Color.Black else InactiveColor,
                    width = withIndicator(selectedTab, index),
                    shape = RoundedCornerShape(30.dp)
                )
            }
        },
    ) {

        TabsContent(
            selectedTab = selectedTab(),
            isClickable = { tab -> tab != selectedTab },
            onTabSelected = onTabSelected
        )
    }
}

@Composable
private fun TabsContent(
    selectedTab: TabContents,
    isClickable: (TabContents) -> Boolean,
    onTabSelected: (TabContents) -> Unit,
) {
    TabContents.entries.forEachIndexed { index, tab ->
        Tab(
            selected = selectedTab.index == index,
            onClick = { onTabSelected(tab) },
            selectedContentColor = Color.Black,
            unselectedContentColor = InactiveColor,
            interactionSource = remember { MutableInteractionSource() },
            enabled = isClickable(tab)
        ) {
            Text(
                text = tab.nameTab,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 40.dp, bottom = 5.dp)
            )
        }
    }
}


@Composable
private fun withIndicator(selectedTab: TabContents, index: Int): Dp {
    val targetValue = if (selectedTab.index == index) 100.dp else 20.dp
    val animatedWidth by animateDpAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "Indicator Animation"
    )
    return animatedWidth
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
