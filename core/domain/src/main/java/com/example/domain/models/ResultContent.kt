package com.example.domain.models

data class ResultContent(
    val page: Int = -1,
    val perPage: Int = -1,
    val content: Set<Content> = emptySet(),
    val nextPage: String? = null,
)



