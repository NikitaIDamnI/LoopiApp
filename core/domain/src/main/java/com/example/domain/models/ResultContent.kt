package com.example.domain.models

data class ResultContent(
    val page: Int,
    val perPage: Int,
    val content: List<Content>,
    val nextPage: String?,
)



