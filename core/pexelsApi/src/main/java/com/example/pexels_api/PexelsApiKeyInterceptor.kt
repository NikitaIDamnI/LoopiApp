package com.example.pexels_api

import okhttp3.Interceptor
import okhttp3.Response

internal class PexelsApiKeyInterceptor(
    private val apiKey: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .addHeader("Authorization", apiKey)
                .build()

        return chain.proceed(request)
    }
}


