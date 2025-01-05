package com.example.home_screen.content

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.home_screen.content.models.Tabs
import com.example.uikit.cards.ContentCard
import com.example.uikit.theme.ColorMainGreen
import com.example.uikit.theme.InactiveColor


@Composable
fun HomeScreenContent(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    HomeScreen(
        modifier = modifier,
        paddingValues = paddingValues
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
) {
    var tabHeight = remember { mutableStateOf(Tabs.MAX_HEIGHT.dp) }
    var selectedTab = remember { mutableStateOf(Tabs.TRENDS) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = 200.dp // Ширина одного элемента
    val columns = (screenWidth / columnWidth).toInt().coerceAtLeast(1)

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
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .width(700.dp)

                .align(Alignment.CenterHorizontally),

            tabs = Tabs.entries,
            selectedTab = selectedTab,
            onTabSelected = { selectedTab.value = it },
            tabHeight = tabHeight,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 5.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(100) { index ->
                ContentCard(
                    modifier = Modifier,
                    heightPhoto = 350.dp,
                    onClickContent = {},
                    onSetting = {}
                )
            }
        }
    }
}

@Composable
internal fun TabRowWithTabs(
    modifier: Modifier = Modifier,
    tabs: List<Tabs>,
    selectedTab: State<Tabs>,
    onTabSelected: (Tabs) -> Unit,
    tabHeight: State<Dp>,
) {
    val selectedTab = selectedTab.value
    val isClickable = tabHeight.value == Tabs.MAX_HEIGHT.dp
    val colorAnimate = remember(tabHeight) {
        derivedStateOf {
            if (tabHeight.value == Tabs.MAX_HEIGHT.dp) {
                ColorMainGreen
            } else {
                ColorMainGreen.copy((tabHeight.value.value / 100 + 0.4f))
            }
        }
    }

    TabRow(
        modifier = modifier
            .height(tabHeight.value),
        selectedTabIndex = selectedTab.index,
        contentColor = ColorMainGreen,
        containerColor = colorAnimate.value,
        indicator = { tabPositions ->
            tabPositions.forEachIndexed { index, tabPosition ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPosition)
                        .background(colorAnimate.value),
                    color = if (index == selectedTab.index) Color.White else InactiveColor,
                    width = withIndicator(selectedTab, index),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selectedTab.index == index,
                onClick = { onTabSelected(tab) },
                selectedContentColor = Color.White,
                unselectedContentColor = InactiveColor,
                interactionSource = remember { MutableInteractionSource() },
                enabled = isClickable
            ) {
                Text(
                    text = tab.nameTab,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun withIndicator(selectedTab: Tabs, index: Int): Dp {
    val targetValue = if (selectedTab.index == index) 100.dp else 20.dp
    val animatedWidth by animateDpAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "Indicator Animation"
    )
    return animatedWidth // Возвращаем значение типа Dp
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
                    delta > 0 -> min(Tabs.MAX_HEIGHT.dp, currentHeight + ((delta / 5).dp))
                    delta < 0 -> max(Tabs.MIN_HEIGHT.dp, currentHeight + ((delta / 5).dp))
                    else -> currentHeight
                }

                if (newHeight != currentHeight) {
                    onTabHeightChange(newHeight)
                }

                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                val currentHeight = tabHeightState()

                val finalHeight = if (currentHeight >= (Tabs.MAX_HEIGHT / 2).dp) {
                    Tabs.MAX_HEIGHT.dp
                } else {
                    Tabs.MIN_HEIGHT.dp
                }

                if (finalHeight != currentHeight) {
                    onTabHeightChange(finalHeight)
                }

                return super.onPreFling(available)
            }
        }
    }
}
