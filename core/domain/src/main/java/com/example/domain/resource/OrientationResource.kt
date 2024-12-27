package com.example.domain.resource

import kotlinx.serialization.SerialName

enum class OrientationResource {

    @SerialName("landscape")
    LANDSCAPE,

    @SerialName("portrait")
    PORTRAIT,

    @SerialName("square")
    SQUARE
}
