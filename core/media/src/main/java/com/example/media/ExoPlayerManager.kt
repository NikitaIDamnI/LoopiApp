package com.example.media

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.common.utils.logD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


interface PlayerManager {
    fun play(uri: String)
    fun pause()
    fun release()
    fun getPlayer(): Player
    val state: StateFlow<StateExoPlayerManager>
}

class ExoPlayerManager(private val application: Application, private val repeatMode: Int
) : PlayerManager {

    private var exoPlayer: ExoPlayer? = null

    private val _state = MutableStateFlow(StateExoPlayerManager())

    override val state: StateFlow<StateExoPlayerManager> get() = _state

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_READY -> updateState { copy(isPlaying = true) }
                Player.STATE_ENDED -> {
                    updateState { copy(isPlaying = false, isShow = false) }
                    logD(this@ExoPlayerManager, "Video ended. isShow = false")
                }
            }
        }

        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()
            updateState { copy(isShow = true) }
            logD(this@ExoPlayerManager, "isShow = true")
        }
    }

    init {
        initializePlayer()
    }

    private fun initializePlayer() {
        if (exoPlayer != null) return
        exoPlayer = ExoPlayer.Builder(application).build().apply {

            val audioAttributes = AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE).build()
            setAudioAttributes(audioAttributes, true)

            addListener(playerListener)
        }
        exoPlayer?.let {
            updateState {
                copy(exoPlayer = it, isInitialized = true)
            }
            logD(this@ExoPlayerManager, "ExoPlayer initialized")
        }
        updateState {
            copy(
                exoPlayer = exoPlayer, isInitialized = true
            )
        }
    }

    override fun play(uri: String) {
        if (!_state.value.isInitialized) {
            logD(this@ExoPlayerManager, "Attempted to play before initializing player")
            return
        }

        exoPlayer?.let { player ->
            when {
                uri == _state.value.contentUrl -> {
                    updateState { copy(isShow = true, isPlaying = true) }
                    player.playWhenReady = true
                    logD(this@ExoPlayerManager, "Resuming video: $uri")
                }

                else -> {

                    updateState { copy(isShow = false) }
                    val mediaItem = MediaItem.fromUri(Uri.parse(uri))
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.playWhenReady = true
                    player.repeatMode = repeatMode
                    updateState {
                        copy(
                            isPlaying = true, contentUrl = uri
                        )
                    }
                    logD(this@ExoPlayerManager, "Playing new video: $uri")
                }
            }
        }
    }

    override fun pause() {
        if (_state.value.isInitialized && _state.value.isPlaying) {
            exoPlayer?.playWhenReady = false
            exoPlayer?.pause()
            updateState { copy(isPlaying = false) }
            logD(this@ExoPlayerManager, "Playback paused. Content: ${_state.value.contentUrl}")
        }
    }

    override fun release() {
        if (_state.value.isInitialized) {
            exoPlayer?.release()
            exoPlayer = null
            updateState {
                copy(
                    exoPlayer = null, isInitialized = false, isPlaying = false, contentUrl = ""
                )
            }
            logD(this@ExoPlayerManager, "Player released")
        }
    }

    private fun updateState(update: StateExoPlayerManager.() -> StateExoPlayerManager) {
        _state.value = _state.value.update()
    }

    override fun getPlayer(): Player {
        return exoPlayer ?: throw RuntimeException("Player not initialized")
    }
}


@Composable
fun rememberExoPlayerManager(
    application: Application,
    repeatMode: Int = Player.REPEAT_MODE_OFF,
): State<ExoPlayerManager> {
    return remember {
        mutableStateOf(
            ExoPlayerManager(
                application, repeatMode
            )
        )
    }
}

data class StateExoPlayerManager(
    val exoPlayer: ExoPlayer? = null,
    val isInitialized: Boolean = false,
    val isPlaying: Boolean = false,
    val isShow: Boolean = false,
    val contentUrl: String = "",
)
