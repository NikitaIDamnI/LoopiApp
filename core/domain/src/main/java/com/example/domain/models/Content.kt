package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
sealed class Content(
    val idContent: Int
) {
    data class Photo(
        val id: Int,
        val width: Int,
        val height: Int,
        val url: String,
        val photographer: String,
        val photographerUrl: String,
        val photographerId: Long,
        val avgColor: Int,
        val src: Src,
        val liked: Boolean,
        val alt: String,

    ): Content(idContent = id)

    @Serializable
    data class Video(
        val id: Int,
        val width: Int,
        val height: Int,
        val url: String,
        val image: String,
        val fullRes: String?,
        val tags: List<String>,
        val duration: Int,
        val user: User,
        val videoFiles: List<VideoFile>,
        val videoPictures: List<VideoPicture>,
    ): Content(idContent = id)
}

@Serializable
data class VideoFile(
    val id: Int,
    val quality: String,
    val fileType: String,
    val width: Int,
    val height: Int,
    val fps: Float,
    val link: String,
)

@Serializable
data class VideoPicture(
    val id: Int,
    val picture: String,
    val nr: Int,
)

@Serializable
data class Src(
    val original: String = "",
    val large2x: String = "",
    val large: String = "",
    val medium: String = "",
    val small: String = "",
    val portrait: String = "",
    val landscape: String = "",
    val tiny: String = "",
)
