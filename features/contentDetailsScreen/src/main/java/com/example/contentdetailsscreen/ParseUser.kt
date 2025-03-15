package com.example.contentdetailsscreen

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun getLatestProfileImage(profileUrl: String): String? {
    return withContext(Dispatchers.IO) {
        try {
            Log.d("getLatestProfileImage", "Загружаем страницу: $profileUrl")

            val document = Jsoup.connect(profileUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9")
                .get()

            // Ищем <img> внутри div с классом "Avatar_avatar__kiIdr"
            val imgElement = document.select("div.Avatar_avatar__kiIdr img").first()
            val imageUrl = imgElement?.attr("src")

            Log.d("getLatestProfileImage", "Найдено изображение: $imageUrl")
            return@withContext imageUrl
        } catch (e: Exception) {
            Log.e("getLatestProfileImage", "Ошибка загрузки аватара", e)
            return@withContext null
        }
    }
}

