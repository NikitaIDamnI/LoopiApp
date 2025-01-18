package com.example.domain.models

data class ResultContent(
    val page: Int = -1,
    val perPage: Int = -1,
    val content: List<Content> = emptyList(),
    val nextPage: String? = null,
)



