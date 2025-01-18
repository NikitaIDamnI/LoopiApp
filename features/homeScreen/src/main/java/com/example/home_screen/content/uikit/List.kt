package com.example.home_screen.content.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.models.Content
import com.example.domain.models.StateLoading
import com.example.home_screen.content.models.StateHomeScreen
import com.example.uikit.cards.ContentCard
import com.vipulasri.aspecto.AspectoGrid

@Composable
fun DynamicColumnGrid(
    stateContent: State<StateHomeScreen>,
    columnWidth: Dp = 150.dp, // Ширина одной колонки
    spacing: Dp = 10.dp,
    onLikeItem: (Content) -> Unit ,
    onClickContent: (Content) -> Unit,
    onSettingContent: () -> Unit,
    onLoadNextContent: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp // Получаем ширину экрана
    val columnCount = (screenWidth / (columnWidth + spacing)).toInt()
        .coerceAtLeast(1) // Рассчитываем количество колонок


    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(spacing), // Отступы между колонками
        verticalArrangement = Arrangement.spacedBy(spacing), // Отступы между строками
        contentPadding = PaddingValues(horizontal = spacing, vertical = spacing)
    ) {
        items(stateContent.value.content) { item ->
            ContentCard(
                modifier = Modifier.fillMaxWidth(), // Каждая колонка занимает всю ширину, рассчитанную для неё
                content = item,
                onClickContent = onClickContent,
                onSetting = onSettingContent,
            )
        }

        item {
            if (stateContent.value.stateLoading is StateLoading.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Black
                    )
                }
            } else {
                SideEffect {
                    onLoadNextContent()
                }
            }
        }


    }

}


@Composable
fun AspectoLazyColum(
    stateContent: State<StateHomeScreen>,
    maxRowHeight: Dp = 450.dp,
    onClickContent: (Content) -> Unit,
    onSettingContent: () -> Unit,
    onLoadNextContent: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AspectoGrid(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            maxRowHeight = maxRowHeight,
            itemPadding = PaddingValues(
                horizontal = 5.dp,
                vertical = 4.dp
            ),
        ) {
            items(
                items = stateContent.value.content,
                aspectRatio = {
                    when(it){
                        is Content.Photo -> {
                          it.width.toFloat() / it.height.toFloat()
                        }
                        is Content.Video -> {
                            (it.width.toFloat() / it.height.toFloat()).coerceAtMost(16f / 9f)
                        }
                    }
                },

            ) { item ->
                ContentCard(
                    modifier = Modifier.fillMaxWidth(),
                    content = item,
                    onClickContent = onClickContent,
                    onSetting = onSettingContent,
                )

            }


            item(0.1f) {
                if (stateContent.value.stateLoading is StateLoading.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                        ,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Black
                        )
                    }
                } else {
                    SideEffect {
                        onLoadNextContent()
                    }
                }
            }
        }
    }

}

