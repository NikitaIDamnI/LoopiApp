package com.example.domain.repository

interface MediaPlayer {
    fun initializePlayer()
    fun play(uri: String)
    fun pause()
    fun stop()
    fun release()
}
