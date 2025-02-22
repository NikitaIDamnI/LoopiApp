package com.example.domain.useCases

import com.example.domain.repository.ContentRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: ContentRepository,
) {

    fun searchPhotos(
        query: String,
        orientation: String? = null,
        size: String? = null,
        color: String? = null,
        locale: String? = null,
        page: Int = 1,
        perPage: Int = 15,
    ) = repository.searchPhotos(query, orientation, size, color, locale, page, perPage)

    fun searchVideos(
        query: String,
        orientation: String? = null,
        size: String? = null,
        locale: String? = null,
        page: Int = 1,
        perPage: Int = 15,
    ) = repository.searchVideos(query, orientation, size, locale, page, perPage)

}
