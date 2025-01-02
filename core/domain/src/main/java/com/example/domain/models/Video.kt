package com.example.domain.models

data class ResultVideo(
    val page: Int,
    val perPage: Int,
    val videos: List<Video>,
    val nextPage: String?,
)
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
)



data class VideoFile(
    val id: Int,
    val quality: String,
    val fileType: String,
    val width: Int,
    val height: Int,
    val fps: Double,
    val link: String,
)

data class VideoPicture(
    val id: Int,
    val picture: String,
    val nr: Int,
)
