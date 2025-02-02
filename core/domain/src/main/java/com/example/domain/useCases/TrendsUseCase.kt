package com.example.domain.useCases

import com.example.domain.models.Content
import com.example.domain.models.ResultContent
import com.example.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class TrendsUseCase @Inject constructor(
    private val repository: ContentRepository,
) {
    operator fun invoke(
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<ResultContent> {
        return combine(
            repository.getPopularPhotos(page, perPage),
            repository.getPopularVideos(page = page, perPage = perPage)
        ) { photos, videos ->
            photos.copy(
                content = photos.content + videos.content,
            )
        }
    }

}
