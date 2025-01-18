package com.example.domain

import com.example.domain.models.ResultContent
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    fun searchPhotos(
        query: String,
        orientation: String? = null,
        size: String? = null,
        color: String? = null,
        locale: String? = null,
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<ResultContent>

    fun getPopularPhotos(
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<ResultContent>


    fun searchVideos(
        query: String,
        orientation: String? = null,
        size: String? = null,
        locale: String? = null,
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<ResultContent>

    fun getPopularVideos(
        minWidth: Int? = null,
        minHeight: Int? = null,
        minDuration: Int? = null,
        maxDuration: Int? = null,
        page: Int = 1,
        perPage: Int = 15,
    ): Flow<ResultContent>

    //fun likeResource(id: Int): Flow<Boolean>
    //fun saveResource(id: Int)

}
