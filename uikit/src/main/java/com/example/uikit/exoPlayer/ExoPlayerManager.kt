package com.example.uikit.exoPlayer

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.common.utils.logD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ExoPlayerManager(
    private val context: Context,
    private val repeatMode: Int
) {
    private var exoPlayer: ExoPlayer? = null
    private val _state = MutableStateFlow(StateExoPlayerManager())
    val state: StateFlow<StateExoPlayerManager> get() = _state

    init {
        initializePlayer()
    }

    private fun initializePlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build().apply {

                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                    .build()
                setAudioAttributes(audioAttributes, true)

                volume = 1f // Включаем звук по умолчанию

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        when (state) {
                            Player.STATE_READY -> {
                                updateState { copy(isPlaying = true) }
                            }
                            Player.STATE_ENDED -> {
                                updateState { copy(isPlaying = false, isShow = false) } // ✅ Скрываем видео при завершении
                                logD(this@ExoPlayerManager, "Video ended. isShow = false")
                            }
                        }
                    }

                    override fun onRenderedFirstFrame() {
                        super.onRenderedFirstFrame()
                        updateState { copy(isShow = true) }
                        logD(this@ExoPlayerManager, "isShow = true")
                    }
                })
            }
            updateState {
                copy(
                    exoPlayer = exoPlayer,
                    isInitialized = true
                )
            }
            logD(this@ExoPlayerManager, "ExoPlayer initialized")
        }
    }

    fun play(uri: String) {
        if (!_state.value.isInitialized) {
            logD(this@ExoPlayerManager, "Attempted to play before initializing player")
            return
        }

        exoPlayer?.let { player ->
            when {
                uri == _state.value.contentUrl -> {
                    // ✅ Если видео было скрыто, снова показываем
                    updateState { copy(isShow = true, isPlaying = true) }
                    player.playWhenReady = true
                    logD(this@ExoPlayerManager, "Resuming video: $uri")
                }
                else -> {
                    // ✅ Загружаем новое видео
                    updateState { copy(isShow = false) }
                    val mediaItem = MediaItem.fromUri(Uri.parse(uri))
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.playWhenReady = true
                    player.repeatMode = repeatMode
                    updateState {
                        copy(
                            isPlaying = true,
                            contentUrl = uri
                        )
                    }
                    logD(this@ExoPlayerManager, "Playing new video: $uri")
                }
            }
        }
    }

    fun pause() {
        if (_state.value.isInitialized && _state.value.isPlaying) {
            exoPlayer?.playWhenReady = false
            exoPlayer?.pause()
            updateState { copy(isPlaying = false) }
            logD(this@ExoPlayerManager, "Playback paused. Content: ${_state.value.contentUrl}")
        }
    }

    fun release() {
        if (_state.value.isInitialized) {
            exoPlayer?.release()
            exoPlayer = null
            updateState {
                copy(
                    exoPlayer = null,
                    isInitialized = false,
                    isPlaying = false,
                    contentUrl = ""
                )
            }
            logD(this@ExoPlayerManager, "Player released")
        }
    }

    private fun updateState(update: StateExoPlayerManager.() -> StateExoPlayerManager) {
        _state.value = _state.value.update()
    }

    fun getPlayer(): Player {
        return exoPlayer ?: throw RuntimeException("Player not initialized")
    }
}


@Composable
fun rememberExoPlayerManager(
    context: Context,
    repeatMode: Int = Player.REPEAT_MODE_OFF
): State<ExoPlayerManager> {
    return remember {
        mutableStateOf(
            ExoPlayerManager(
                context,
                repeatMode
            )
        )
    }
}

data class StateExoPlayerManager(
    val exoPlayer: ExoPlayer? = null,
    val isInitialized: Boolean = false,
    val isPlaying: Boolean = false,
    val isShow: Boolean = false,
    val contentUrl: String = ""
)

