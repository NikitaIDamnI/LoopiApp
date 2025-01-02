package com.example.di

import com.example.data.repisitory.RepositoryImpl
import com.example.domain.Repository
import com.example.pexels_api.PexelsApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindsRepository(repositoryImpl: RepositoryImpl): Repository

    companion object{
        @Provides
        fun providesPexelsApi(
            okHttpClient: OkHttpClient
        ): PexelsApi {
            return PexelsApi(
                baseUrl = BuildConfig.BASE_URL,
                apiKey = BuildConfig.PEXELS_API_KEY,
                okHttpClient = okHttpClient
            )

        }
        @Provides
        @Singleton
        fun provideOkhttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }
    }
}
