package com.example.uikit.models

import com.example.domain.models.*
import com.example.domain.models.Content
import kotlinx.collections.immutable.persistentListOf




fun Content.toUI(): ContentUI {
    return when (this) {
        is Content.Photo -> this.toUI()
        is Content.Video -> this.toUI()
    }
}

fun Content.Photo.toUI(): ContentUI.PhotoUI {
    return ContentUI.PhotoUI(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        src = src.toUI(), // ✅ Конвертируем `Src`
        liked = liked,
        alt = alt
    )
}

fun Content.Video.toUI(): ContentUI.VideoUI {
    return ContentUI.VideoUI(
        id = id,
        width = width,
        height = height,
        url = url,
        image = image,
        fullRes = fullRes,
        tags = persistentListOf(*tags.toTypedArray()), // ✅ Преобразуем `List` в `PersistentList`
        duration = duration,
        user = user.toUI(), // ✅ Конвертируем `User`
        videoFiles = persistentListOf(*videoFiles.map { it.toUI() }.toTypedArray()), // ✅ Конвертируем `VideoFile`
        videoPictures = persistentListOf(*videoPictures.map { it.toUI() }.toTypedArray()) // ✅ Конвертируем `VideoPicture`
    )
}

// ✅ Маппер для `Src`
fun Src.toUI(): SrcUI {
    return com.example.uikit.models.SrcUI(
        original = original,
        large2x = large2x,
        large = large,
        medium = medium,
        small = small,
        portrait = portrait,
        landscape = landscape,
        tiny = tiny
    )
}

// ✅ Маппер для `VideoFile`
fun VideoFile.toUI(): VideoFileUI {
    return com.example.uikit.models.VideoFileUI(
        id = id,
        quality = quality,
        fileType = fileType,
        width = width,
        height = height,
        fps = fps,
        link = link
    )
}

// ✅ Маппер для `VideoPicture`
fun VideoPicture.toUI(): VideoPictureUI {
    return  VideoPictureUI(
        id = id,
        picture = picture,
        nr = nr
    )
}

// ✅ Маппер для `User`
fun User.toUI(): UserUI {
    return UserUI(
        id = id,
        name = name,
        url = url
    )
}
