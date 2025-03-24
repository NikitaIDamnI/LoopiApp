package com.example.pexels_api

import androidx.annotation.IntRange
import com.example.pexels_api.models.PhotoDto
import com.example.pexels_api.models.PhotoResultDto
import com.example.pexels_api.models.VideoDto
import com.example.pexels_api.models.VideoResultDto
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PexelsApi {

    @GET("v1/search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("orientation") orientation: String? = null,
        @Query("size") size: String? = null,
        @Query("color") color: String? = null,
        @Query("locale") locale: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("per_page") @IntRange(from = 15, to = 80) perPage: Int = 15,
    ): Result<PhotoResultDto>

    @GET("v1/curated")
    suspend fun getPopularPhotos(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("per_page") @IntRange(from = 15, to = 80) perPage: Int = 15,
    ): Result<PhotoResultDto>

    @GET("v1/photos")
    suspend fun getPhotosById(
        @Path("id") id: Int,
    ): Result<PhotoDto>

    @GET("videos/search")
    suspend fun searchVideos(
        @Query("query") query: String,
        @Query("orientation") orientation: String? = null,
        @Query("size") size: String? = null,
        @Query("locale") locale: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("per_page") @IntRange(from = 15, to = 80) perPage: Int = 15,
    ): Result<VideoResultDto>

    @GET("videos/popular")
    suspend fun getPopularVideos(
        @Query("min_width") minWidth: Int? = null,
        @Query("min_height") minHeight: Int? = null,
        @Query("min_duration") minDuration: Int? = null,
        @Query("max_duration") maxDuration: Int? = null,
        @Query("page") page: Int? = 1,
        @Query("per_page") perPage: Int? = 15,
    ): Result<VideoResultDto>

    @GET("videos/videos/{id}")
    suspend fun getVideoById(
        @Path("id") id: Int,
    ): VideoDto


}

fun PexelsApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): PexelsApi = retrofit(baseUrl, apiKey, okHttpClient, json).create()

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?,
    json: Json = Json,
): Retrofit {
    val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())

    val modifiedOkHttpClient =
        (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
            .addInterceptor(PexelsApiKeyInterceptor(apiKey))
            .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(modifiedOkHttpClient)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .build()

}
