package com.example.domain.models

data class ResultPhoto(
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>,
    val nextPage: String?,
)

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Int,
    val avgColor: String,
    val src: Src,
    val liked: Boolean,
    val alt: String,
)

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
