package com.example.domain.useCases

import com.example.domain.ContentRepository
import com.example.domain.models.Content
import com.example.domain.models.ResultContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class TrendsUseCase @Inject constructor(
    private val repository: ContentRepository,
) {

    fun getTrendsContent(
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<ResultContent> {
        return combine(
            repository.getPopularPhotos(page, perPage),
            repository.getPopularVideos(page = page, perPage = perPage)
        ) { photos, videos ->
            photos.copy(
                content = shuffleContent(photos.content, videos.content),
            )
        }
    }


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
        perPage: Int = 15,
    ) = repository.getPopularPhotos(page, perPage)
}

private fun shuffleContent(continent1: List<Content>, continent2: List<Content>): List<Content> {
    return (continent1 + continent2).shuffled()
}
