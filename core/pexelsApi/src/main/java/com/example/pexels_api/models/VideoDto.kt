package com.example.pexels_api.models

import kotlinx.serialization.SerialName

data class VideoResultDto(
    @SerialName("videos")
    val videos: List<VideoDto>,

    @SerialName("url")
    val url: String,

    @SerialName("page")
    val page: Int,

    @SerialName("per_page")
    val perPage: Int,

    @SerialName("total_results")
    val totalResults: Int,

    @SerialName("prev_page")
    val prevPage: String?,

    @SerialName("next_page")
    val nextPage: String?,
)

data class VideoDto(
    @SerialName("id")
    val id: Int,

    @SerialName("width")
    val width: Int,

    @SerialName("height")
    val height: Int,

    @SerialName("url")
    val url: String,

    @SerialName("image")
    val image: String,

    @SerialName("full_res")
    val fullRes: String?,

    @SerialName("tags")
    val tags: List<String>,

    @SerialName("duration")
    val duration: Int,

    @SerialName("user")
    val user: UserDto,

    @SerialName("video_files")
    val videoFiles: List<VideoFileDto>,

    @SerialName("video_pictures")
    val videoPictures: List<VideoPictureDto>,
)

data class UserDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String,
)

data class VideoFileDto(
    @SerialName("id")
    val id: Int,

    @SerialName("quality")
    val quality: String,

    @SerialName("file_type")
    val fileType: String,

    @SerialName("width")
    val width: Int,

    @SerialName("height")
    val height: Int,

    @SerialName("fps")
    val fps: Double,

    @SerialName("link")
    val link: String,
)

data class VideoPictureDto(
    @SerialName("id")
    val id: Int,

    @SerialName("picture")
    val picture: String,

    @SerialName("nr")
    val nr: Int,
)
