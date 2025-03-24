package com.example.uikit.home

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.models.VideoType
import com.example.media.rememberExoPlayerManager
import com.example.uikit.cards.CardLoading
import com.example.uikit.cards.ContentCard
import com.example.uikit.models.ContentUI
import com.example.uikit.models.ContentUI.VideoUI.Companion.getVideo
import com.vipulasri.aspecto.AspectoGrid
import kotlinx.collections.immutable.PersistentList
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

@SuppressLint("SuspiciousIndentation")
@Composable
fun AspectoLazyColum(
    modifier: Modifier = Modifier,
    contents: () -> PersistentList<ContentUI>,
    isLoading: () -> Boolean,
    maxRowHeight: Dp = 450.dp,
    onClickContent: (ContentUI) -> Unit,
    onSettingContent: () -> Unit,
    onLoadNextContent: () -> Unit,
) {
    val applicationContext = LocalContext.current.applicationContext as Application
    val exoPlayerManager = rememberExoPlayerManager(
        application = applicationContext
    )
    val statePlayer = exoPlayerManager.value.state.collectAsState()

    AspectoGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 5.dp, end = 5.dp),
        maxRowHeight = maxRowHeight,
        itemPadding = PaddingValues(
            horizontal = 3.dp,
            vertical = 3.dp
        ),
    ) {
        items(
            items = contents(),
            key = { it.idContent },
            aspectRatio = {
                when (it) {
                    is ContentUI.PhotoUI -> it.width.toFloat() / it.height.toFloat()
                    is ContentUI.VideoUI -> {
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
                isPlayVideo = { video -> statePlayer.value.contentUrl == video && statePlayer.value.isPlaying },
                isShowVideo = { video -> statePlayer.value.contentUrl == video && statePlayer.value.isShow },
                onPlayVideo = { url ->
                    exoPlayerManager.value.play(url)
                },
                onPauseVideo = {
                    exoPlayerManager.value.pause()
                }
            )
        }

        if (isLoading()) {
            items(
                items = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                key = { it },
                aspectRatio = { generateRandomAspectRatio() }
            ) {
                CardLoading(modifier)
            }

        }
        item(.1f) {
            if (!isLoading()) {
                SideEffect {
                    onLoadNextContent()
                }
            }
        }

    }
}

fun generateRandomAspectRatio(): Float {
    val width = Random.nextInt(200, 400)
    val height = Random.nextInt(200, 400)
    val maxAspect = 16f / 9f
    val minAspect = 9f / 16f
    return max(minAspect, min(maxAspect, width.toFloat() / height.toFloat()))
}




