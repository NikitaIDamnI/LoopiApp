package com.example.uikit.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.models.Content
import com.example.uikit.cards.ContentCard
import kotlin.collections.forEach

@Composable
fun ListContent(
    modifier: Modifier,
    content: List<Content>,
    columnWidth: Dp = 150.dp,
    spacing: Dp = 10.dp,
    onClickContent: (String) -> Unit,
    onSettingContent: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxColumnCount = (screenWidth / (columnWidth + spacing)).toInt().coerceAtLeast(1) // Минимум 1 колонка

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        item {
            Layout(
                content = {
                    content.forEach { item ->
                        ContentCard(
                            modifier = Modifier,
                            content = item,
                            onClickContent = onClickContent,
                            onSetting = onSettingContent
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 2.dp, start = 2.dp, end = 2.dp)
            ) { measurables, constraints ->

                val totalSpacing = (spacing.toPx() * (maxColumnCount - 1))
                val columnWidthPx = ((constraints.maxWidth - totalSpacing) / maxColumnCount).toInt()
                val itemConstraints = Constraints(
                    minWidth = 0,
                    maxWidth = columnWidthPx,
                    minHeight = 0,
                    maxHeight = Constraints.Infinity
                )

                val columnHeights = IntArray(maxColumnCount) { 0 }

                val placeables = measurables.map { measurable ->
                    val column = columnHeights.indexOfFirst { it == columnHeights.minOrNull() }
                    val placeable = measurable.measure(itemConstraints)
                    columnHeights[column] += placeable.height + spacing.toPx().toInt()
                    column to placeable
                }

                val layoutHeight = columnHeights.maxOrNull() ?: constraints.minHeight

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
    }
}
