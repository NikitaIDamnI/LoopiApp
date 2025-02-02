package com.example.home_screen.content.uikit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.models.Content
import com.example.domain.models.Content.Video.Companion.getVideo
import com.example.domain.models.VideoType
import com.example.uikit.cards.ContentCard
import com.example.uikit.exoPlayer.rememberExoPlayerManager
import com.vipulasri.aspecto.AspectoGrid
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AspectoLazyColum(
    modifier: Modifier = Modifier,
    contents: () -> List<Content>,
    isLoading: () -> Boolean,
    maxRowHeight: Dp = 450.dp,
    onClickContent: (Content) -> Unit,
    onSettingContent: () -> Unit,
    onLoadNextContent: () -> Unit,
) {
    val exoPlayerManager = rememberExoPlayerManager(LocalContext.current)
    val statePlayer = exoPlayerManager.value.state.collectAsState()


    Surface(
        modifier = modifier.fillMaxSize(),
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
                items = contents(),
                key = { it.idContent },
                aspectRatio = {
                    when (it) {
                        is Content.Photo -> it.width.toFloat() / it.height.toFloat()
                        is Content.Video -> {
                            val video = it.getVideo(VideoType.SD)
                            (video.width.toFloat() / video.height.toFloat()).coerceAtMost(16f / 9f)
                        }
                    }
                }
            ) { item ->
                ContentCard(
                    modifier = Modifier.fillMaxWidth(),
                    content = item,
                    exoPlayerManager = exoPlayerManager.value,
                    onClickContent = onClickContent,
                    isPlayVideo = { video -> statePlayer.value.contentUrl == video && statePlayer.value.isPlaying},
                    isShowVideo = { video -> statePlayer.value.contentUrl == video && statePlayer.value.isShow },
                    onPlayVideo = { url ->
                        exoPlayerManager.value.play(url)
                    },
                    onPauseVideo = {
                        exoPlayerManager.value.pause()
                    }
                )
            }
            item(0.1f) {
                if (isLoading()) {
                    Box(
                        modifier = Modifier.fillMaxSize()
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

