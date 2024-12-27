package com.example.pexels_api.models

import kotlinx.serialization.SerialName

data class PhotoResultDto(
    @SerialName("total_results")
    val totalResults: Int,

    @SerialName("page")
    val page: Int,

    @SerialName("per_page")
    val perPage: Int,

    @SerialName("photos")
    val photos: List<PhotoDto>,

    @SerialName("next_page")
    val nextPage: String?,

    @SerialName("prev_page")
    val prevPage: String?,
)


data class PhotoDto(
    @SerialName("id")
    val id: Int,

    @SerialName("width")
    val width: Int,

    @SerialName("height")
    val height: Int,

    @SerialName("url")
    val url: String,

    @SerialName("photographer")
    val photographer: String,

    @SerialName("photographer_url")
    val photographerUrl: String,

    @SerialName("photographer_id")
    val photographerId: Int,

    @SerialName("avg_color")
    val avgColor: String,

    @SerialName("src")
    val src: SrcDto,

    @SerialName("liked")
    val liked: Boolean,

    @SerialName("alt")
    val alt: String,
)

data class SrcDto(
    @SerialName("original")
    val original: String,

    @SerialName("large2x")
    val large2x: String,

    @SerialName("large")
    val large: String,

    @SerialName("medium")
    val medium: String,

    @SerialName("small")
    val small: String,

    @SerialName("portrait")
    val portrait: String,

    @SerialName("landscape")
    val landscape: String,

    @SerialName("tiny")
    val tiny: String,
)

