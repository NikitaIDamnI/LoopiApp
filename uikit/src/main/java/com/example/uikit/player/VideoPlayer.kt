package com.example.uikit.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.media.ExoPlayerManager

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    placeholderUrl: String,
    exoPlayerManager: ExoPlayerManager,
    modifier: Modifier = Modifier,
    isPlayVideo: () -> Boolean,
    isShowVideo: () -> Boolean,
) {
    val context = LocalContext.current

    Box(modifier = modifier) {
        if (!isPlayVideo() && !isShowVideo()) {
            AsyncImage(
                model = placeholderUrl,
                contentDescription = "Placeholder",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayerManager.getPlayer()
                        useController = false
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
            if (!isShowVideo()) {
                Box {
                    AsyncImage(
                        model = placeholderUrl,
                        contentDescription = "Placeholder",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (isPlayVideo() || isShowVideo()) {
                exoPlayerManager.pause()
            }
        }
    }


}

