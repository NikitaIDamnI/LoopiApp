package com.example.pexels_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResultDto(
    @SerialName("page") val page: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("prev_page") val prevPage: String? ="",
    @SerialName("videos") val videos: List<VideoDto>,
    @SerialName("total_results") val totalResults: Int,
    @SerialName("next_page") val nextPage: String?,
    @SerialName("url") val url: String,
)

@Serializable
data class VideoDto(
    @SerialName("id") val id: Int,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("duration") val duration: Int,
    @SerialName("full_res") val fullRes: String?,
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("url") val url: String,
    @SerialName("image") val image: String,
    @SerialName("avg_color") val avgColor: String?,
    @SerialName("user") val user: UserDto,
    @SerialName("video_files") val videoFiles: List<VideoFileDto>,
    @SerialName("video_pictures") val videoPictures: List<VideoPictureDto>,
)

@Serializable
data class UserDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("url") val url: String,
)

@Serializable
data class VideoFileDto(
    @SerialName("id") val id: Int,
    @SerialName("quality") val quality: String,
    @SerialName("file_type") val fileType: String,
    @SerialName("width") val width: Int?,
    @SerialName("height") val height: Int?,
    @SerialName("fps") val fps: Float?,
    @SerialName("link") val link: String,
    @SerialName("size") val size: Long,
)

@Serializable
data class VideoPictureDto(
    @SerialName("id") val id: Int,
    @SerialName("nr") val nr: Int,
    @SerialName("picture") val picture: String,
)
