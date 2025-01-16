package com.example.data.repisitory

import com.example.common.utils.logD
import com.example.data.mapper.toDomain
import com.example.domain.ContentRepository
import com.example.domain.models.ResultContent
import com.example.pexels_api.PexelsApi
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ContentRepositoryImpl @Inject constructor(
    private val pexelsApi: PexelsApi,
) : ContentRepository {

    override fun searchPhotos(
        query: String,
        orientation: String?,
        size: String?,
        color: String?,
        locale: String?,
        page: Int,
        perPage: Int,
    ): Flow<Result<ResultContent>> = callbackFlow {
        pexelsApi.searchPhotos(query, orientation, size, color, locale, page, perPage)
            .onSuccess { dto ->
                trySend(Result.success(dto.toDomain()))
            }
            .onFailure {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun getPopularPhotos(
        page: Int,
        perPage: Int,
    ): Flow<ResultContent> = callbackFlow {
        logD(this@ContentRepositoryImpl, "getPopularPhotos : Start")
        pexelsApi.getPopularPhotos(page, perPage)
            .onSuccess { dto ->
                logD(this@ContentRepositoryImpl, dto.toString())
                trySend(dto.toDomain())
            }
            .onFailure {
                trySend(throw RuntimeException(it))
            }
        awaitClose()
    }


    override fun searchVideos(
        query: String,
        orientation: String?,
        size: String?,
        locale: String?,
        page: Int,
        perPage: Int,
    ): Flow<Result<ResultContent>> = callbackFlow {
       pexelsApi.searchVideos(query, orientation, size, locale, page)
            .onSuccess { dto ->
                trySend(Result.success(dto.toDomain()))
            }
            .onFailure {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun getPopularVideos(
        minWidth: Int?,
        minHeight: Int?,
        minDuration: Int?,
        maxDuration: Int?,
        page: Int,
        perPage: Int,
    ): Flow<Result<ResultContent>> = callbackFlow {
        pexelsApi.getPopularVideos(minWidth, minHeight, minDuration, maxDuration, page, perPage)
            .onSuccess { dto ->
                logD(this@ContentRepositoryImpl, dto.toString())

                trySend(Result.success(dto.toDomain()))
            }
            .onFailure {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

}
