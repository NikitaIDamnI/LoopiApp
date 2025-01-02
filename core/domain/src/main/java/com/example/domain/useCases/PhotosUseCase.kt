package com.example.domain.useCases

import com.example.domain.Repository
import javax.inject.Inject


class PhotosUseCase @Inject constructor(
    private val repository: Repository,
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

    fun getPhotos(
        page: Int = 1,
        perPage: Int = 15
    ) = repository.getPopularPhotos(page, perPage)
}
