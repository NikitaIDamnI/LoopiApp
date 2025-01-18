package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
sealed class Content(
    val idContent: Int,
) {
    data class Photo(
        val id: Int,
        val width: Int,
        val height: Int,
        val url: String,
        val photographer: String,
        val photographerUrl: String,
        val photographerId: Long,
        val avgColor: Int,
        val src: Src,
        val liked: Boolean,
        val alt: String,

        ) : Content(idContent = id)

    @Serializable
    data class Video(
        val id: Int,
        val width: Int,
        val height: Int,
        val url: String,
        val image: String,
        val fullRes: String?,
        val tags: List<String>,
        val duration: Int,
        val user: User,
        val videoFiles: List<VideoFile>,
        val videoPictures: List<VideoPicture>,
    ) : Content(idContent = id) {
        companion object{
            fun Content.Video.getVideo(type: VideoType): VideoFile {
                return getVideoByType(type)
            }

            private fun Content.Video.getVideoByType(type: VideoType): VideoFile {
                // Логика приоритетного выбора файла
                val file = when (type) {
                    VideoType.SD -> videoFiles
                        .filter { it.quality == "sd" }
                        .maxByOrNull { it.width * it.height } // SD: Минимальное разрешение
                        ?: videoFiles
                            .filter { it.quality == "hd" || it.quality == "uhd" } // Если SD нет, ищем HD или UHD
                            .minByOrNull { it.width * it.height }

                    VideoType.HD -> videoFiles
                        .filter { it.quality == "hd" }
                        .minByOrNull { it.width * it.height } // HD: Минимальное разрешение
                        ?: videoFiles
                            .filter { it.quality == "uhd" || it.quality == "sd" } // Если HD нет, ищем UHD или SD
                            .maxByOrNull { it.width * it.height }

                    VideoType.UHD -> videoFiles
                        .filter { it.quality == "uhd" }
                        .maxByOrNull { it.width * it.height } // UHD: Максимальное разрешение
                        ?: videoFiles
                            .filter { it.quality == "hd" || it.quality == "sd" } // Если UHD нет, ищем HD или SD
                            .maxByOrNull { it.width * it.height }
                }

                // Если подходящий файл не найден, выбрасываем исключение
                return file ?: throw IllegalStateException("No video files available")
            }
        }
    }
}

@Serializable
data class VideoFile(
    val id: Int,
    val quality: String,
    val fileType: String,
    val width: Int,
    val height: Int,
    val fps: Float,
    val link: String,
)

@Serializable
data class VideoPicture(
    val id: Int,
    val picture: String,
    val nr: Int,
)

@Serializable
data class Src(
    val original: String = "",
    val large2x: String = "",
    val large: String = "",
    val medium: String = "",
    val small: String = "",
    val portrait: String = "",
    val landscape: String = "",
    val tiny: String = "",
)