package com.example.home_screen.home.tab

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uikit.home.TabContents
import com.example.uikit.theme.InactiveColor


@Composable
fun TabRowCategoryContent(
    modifier: Modifier = Modifier,
    paddingTab: Dp,
    selectedTab: () -> TabContents,
    onTabSelected: (TabContents) -> Unit,
    tabHeight: State<Dp>,
) {


    Row(
        modifier = modifier.height(tabHeight.value),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabContents.entries.forEachIndexed { index, tab ->
            val color =
                if (index == selectedTab().index) MaterialTheme.colorScheme.onBackground else InactiveColor
            Card(
                modifier = Modifier
                    .padding(top = paddingTab)
                    .width(100.dp)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            if (index != selectedTab().index) {
                                onTabSelected(tab)
                            }
                        }
                    },
                colors = CardDefaults.cardColors(Color.Transparent),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = tab.nameTab,
                    color = color,
                    fontSize = 15.sp,
                )
                Canvas(
                    modifier = Modifier
                        .padding(3.dp)
                        .width(withIndicator(selectedTab(), index))
                        .align(Alignment.CenterHorizontally)
                        .height(3.dp) // Высота Canvas

                ) {
                    val strokeWidth = size.height.coerceAtLeast(1f)
                    val offset = strokeWidth / 2

                    drawLine(
                        color = color,
                        start = Offset(-offset, size.height / 2),
                        end = Offset(
                            size.width + offset,
                            size.height / 2
                        ),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )

                }
            }


        }


    }


}

@Composable
private fun withIndicator(selectedTab: TabContents, index: Int): Dp {
    val targetValue = if (selectedTab.index == index) 70.dp else 20.dp
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



