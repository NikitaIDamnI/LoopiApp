package com.example.domain.useCases

import com.example.domain.ContentRepository
import javax.inject.Inject


class TrendsUseCase @Inject constructor(
    private val repository: ContentRepository,
) {
    fun getTrendsVideos(
        minWidth: Int? = null,
        minHeight: Int? = null,
        minDuration: Int? = null,
        maxDuration: Int? = null,
        page: Int = 1,
        perPage: Int = 15,
    ) = repository.getPopularVideos(minWidth, minHeight, minDuration, maxDuration, page, perPage)

    fun getTrendsPhotos(
        page: Int = 1,
        perPage: Int = 15
    ) = repository.getPopularPhotos(page, perPage)
}
