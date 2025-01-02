package com.example.domain.useCases

import com.example.domain.Repository
import javax.inject.Inject

class VideosUseCase @Inject constructor(
    private val repository: Repository,
) {
    fun searchVideos(
        query: String,
        orientation: String? = null,
        size: String? = null,
        locale: String? = null,
        page: Int = 1,
        perPage: Int = 15,
    ) = repository.searchVideos(query, orientation, size, locale, page, perPage)


    fun getPopularVideos(
        minWidth: Int? = null,
        minHeight: Int? = null,
        minDuration: Int? = null,
        maxDuration: Int? = null,
        page: Int = 1,
        perPage: Int = 15,
    ) = repository.getPopularVideos(minWidth, minHeight, minDuration, maxDuration, page, perPage)

}
