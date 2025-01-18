package com.example.uikit.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.models.Content
import com.example.domain.models.StateLoading
import com.example.uikit.cards.ContentCard
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.collections.forEach


@Composable
fun CustomListContent(
    modifier: Modifier,
    content: List<Content>,
    columnWidth: Dp = 150.dp,
    spacing: Dp = 10.dp,
    onClickContent: (Content) -> Unit,
    onSettingContent: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxColumnCount = (screenWidth / (columnWidth + spacing)).toInt().coerceAtLeast(1)

    Layout(
        content = {
            content.forEach { item ->
                ContentCard(
                    modifier = Modifier,
                    content = item,
                    onClickContent = onClickContent,
                    onSetting = onSettingContent,
                )
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val totalSpacing = (spacing.toPx() * (maxColumnCount - 1))
        val columnWidthPx = ((constraints.maxWidth - totalSpacing) / maxColumnCount).toInt()
        val itemConstraints = Constraints(
            minWidth = 0,
            maxWidth = columnWidthPx,
            minHeight = 0,
            maxHeight = constraints.maxHeight // Ограничиваем высоту
        )

        val columnHeights = IntArray(maxColumnCount) { 0 }

        val placeables = measurables.map { measurable ->
            val column = columnHeights.indexOfFirst { it == columnHeights.minOrNull() }
            val placeable = measurable.measure(itemConstraints)
            columnHeights[column] += placeable.height + spacing.toPx().toInt()
            column to placeable
        }

        val layoutHeight = columnHeights.maxOrNull()?.coerceAtMost(constraints.maxHeight) ?: constraints.minHeight

        layout(constraints.maxWidth, layoutHeight) {
            val columnX = IntArray(maxColumnCount) { column ->
                (column * (columnWidthPx + spacing.toPx())).toInt()
            }

            val columnY = IntArray(maxColumnCount) { 0 }
            placeables.forEach { (column, placeable) ->
                placeable.placeRelative(
                    x = columnX[column],
                    y = columnY[column]
                )
                columnY[column] += placeable.height + spacing.toPx().toInt()
            }
        }
    }
}




