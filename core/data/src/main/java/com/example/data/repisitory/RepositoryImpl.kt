package com.example.data.repisitory

import android.util.Log
import com.example.data.mapper.toDomain
import com.example.domain.Repository
import com.example.domain.models.ResultPhoto
import com.example.domain.models.ResultVideo
import com.example.pexels_api.PexelsApi
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.log

class RepositoryImpl @Inject constructor(
    private val pexelsApi: PexelsApi,
    // private val firebaseServises: FirebaseServises,
    //private val database: Database
) : Repository {

    override fun searchPhotos(
        query: String,
        orientation: String?,
        size: String?,
        color: String?,
        locale: String?,
        page: Int,
        perPage: Int,
    ): Flow<Result<ResultPhoto>> = callbackFlow {
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
    ): Flow<Result<ResultPhoto>> = callbackFlow {
        pexelsApi.getPopularPhotos(page, perPage)
            .onSuccess { dto ->
                trySend(Result.success(dto.toDomain()))
            }
            .onFailure {
                trySend(Result.failure(it))
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
    ): Flow<Result<ResultVideo>> = callbackFlow {
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
    ): Flow<Result<ResultVideo>> = callbackFlow {
        pexelsApi.getPopularVideos(minWidth, minHeight, minDuration, maxDuration, page, perPage)
            .onSuccess { dto ->
                trySend(Result.success(dto.toDomain()))
            }
            .onFailure {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

}
