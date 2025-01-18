package com.example.data.mapper

import com.example.domain.models.Content.Photo
import com.example.domain.models.Content.Video
import com.example.domain.models.ResultContent
import com.example.domain.models.Src
import com.example.domain.models.User
import com.example.domain.models.VideoFile
import com.example.domain.models.VideoPicture
import com.example.pexels_api.models.PhotoDto
import com.example.pexels_api.models.PhotoResultDto
import com.example.pexels_api.models.SrcDto
import com.example.pexels_api.models.UserDto
import com.example.pexels_api.models.VideoDto
import com.example.pexels_api.models.VideoFileDto
import com.example.pexels_api.models.VideoPictureDto
import com.example.pexels_api.models.VideoResultDto

fun PhotoResultDto.toDomain(): ResultContent = ResultContent(
    page = page,
    perPage = perPage,
    content = photos.map { it.toDomain(page = page, nextPage = nextPage) },
    nextPage = nextPage
)

fun PhotoDto.toDomain(): Photo = Photo(
    id = id,
    width = width ?: 0,
    height = height ?: 0,
    url = url ?: "",
    photographer = photographer ?: "",
    photographerUrl = photographerUrl ?: "",
    photographerId = photographerId ?: -1L,
    avgColor = avgColor?.toAndroidColor() ?: 0,
    src = src?.toDomain() ?: Src(),
    liked = liked,
    alt = alt ?: "",
    )

fun SrcDto.toDomain(): Src = Src(
    original = original ?: "",
    large2x = large2x ?: "",
    large = large ?: "",
    small = small ?: "",
    portrait = portrait ?: "",
    landscape = landscape ?: "",
    tiny = tiny ?: "",
    medium = medium ?: ""
)

fun VideoResultDto.toDomain(): ResultContent = ResultContent(
    page = page,
    perPage = perPage,
    content = videos.map { it.toDomain() },
    nextPage = nextPage
)

fun VideoDto.toDomain(): Video = Video(
    id = id,
    width = width,
    height = height,
    url = url,
    image = image,
    fullRes = fullRes,
    tags = tags,
    duration = duration,
    user = user.toDomain(),
    videoFiles = videoFiles.map { it.toDomain() },
    videoPictures = videoPictures.map { it.toDomain() },
    )


fun UserDto.toDomain(): User = User(
    id = id,
    name = name,
    url = url,
)

fun VideoFileDto.toDomain(): VideoFile = VideoFile(
    id = id,
    quality = quality,
    fileType = fileType,
    width = width ?: 1000,
    height = height ?: 1000,
    fps = fps ?: 25.0f,
    link = link,
)

fun VideoPictureDto.toDomain(): VideoPicture = VideoPicture(
    id = id,
    picture = picture,
    nr = nr
)

fun String.toAndroidColor(): Int {
    return android.graphics.Color.parseColor(this)
}


