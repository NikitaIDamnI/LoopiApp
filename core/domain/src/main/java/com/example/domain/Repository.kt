package com.example.domain

import androidx.annotation.IntRange
import com.example.domain.models.ResultPhoto
import com.example.domain.models.ResultVideo
import kotlinx.coroutines.flow.Flow

interface Repository {
     fun searchPhotos(
        query: String,
        orientation: String? = null,
        size: String? = null,
        color: String? = null,
        locale: String? = null,
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<Result<ResultPhoto>>

     fun getPopularPhotos(
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<Result<ResultPhoto>>


     fun searchVideos(
        query: String,
        orientation: String? = null,
        size: String? = null,
        locale: String? = null,
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<Result<ResultVideo>>

     fun getPopularVideos(
        minWidth: Int? = null,
        minHeight: Int? = null,
        minDuration: Int? = null,
        maxDuration: Int? = null,
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<Result<ResultVideo>>

     //fun likeResource(id: Int): Flow<Boolean>
     //fun saveResource(id: Int)

}
