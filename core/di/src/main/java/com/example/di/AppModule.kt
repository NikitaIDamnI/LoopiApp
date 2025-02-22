package com.example.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.data.repisitory.ContentRepositoryImpl
import com.example.domain.repository.ContentRepository
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
    fun bindsRepository(repositoryImpl: ContentRepositoryImpl): ContentRepository

    companion object{

        @Singleton
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
        fun provideStoreFactory(): StoreFactory = DefaultStoreFactory()

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
