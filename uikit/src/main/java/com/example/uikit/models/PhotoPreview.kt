package com.example.uikit.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.domain.models.VideoType
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize


sealed class ContentUI(
    val idContent: Int,
) : Parcelable {
    @Immutable
    @Parcelize
    data class PhotoUI(
        val id: Int = -1,
        val width: Int = -1,
        val height: Int = -1,
        val url: String = "",
        val photographer: String= "",
        val photographerUrl: String= "",
        val photographerId: Long = -1,
        val avgColor: Int= -1,
        val src: SrcUI = SrcUI(),
        val liked: Boolean = false,
        val alt: String = "",
    ) : ContentUI(idContent = id)

    @Immutable
    @Parcelize
    data class VideoUI(
        val id: Int,
        val width: Int,
        val height: Int,
        val url: String,
        val image: String,
        val fullRes: String?,
        val tags: PersistentList<String>,
        val duration: Int,
        val user: UserUI,
        val videoFiles: PersistentList<VideoFileUI>,
        val videoPictures: PersistentList<VideoPictureUI>,
    ) : ContentUI(idContent = id) {

        companion object {
            fun ContentUI.VideoUI.getVideo(type: VideoType): VideoFileUI {
                return getVideoByType(type)
            }

            private fun ContentUI.VideoUI.getVideoByType(type: VideoType): VideoFileUI {
                return when (type) {
                    VideoType.SD -> videoFiles.filter { it.quality == "sd" }
                        .maxByOrNull { it.width * it.height }
                        ?: videoFiles.filter { it.quality == "hd" || it.quality == "uhd" }
                            .minByOrNull { it.width * it.height }

                    VideoType.HD -> videoFiles.filter { it.quality == "hd" }
                        .minByOrNull { it.width * it.height }
                        ?: videoFiles.filter { it.quality == "uhd" || it.quality == "sd" }
                            .maxByOrNull { it.width * it.height }

                    VideoType.UHD -> videoFiles.filter { it.quality == "uhd" }
                        .maxByOrNull { it.width * it.height }
                        ?: videoFiles.filter { it.quality == "hd" || it.quality == "sd" }
                            .maxByOrNull { it.width * it.height }
                } ?: throw IllegalStateException("No video files available")
            }
        }
    }
}

@Immutable
@Parcelize
data class VideoFileUI(
    val id: Int,
    val quality: String,
    val fileType: String,
    val width: Int,
    val height: Int,
    val fps: Float,
    val link: String,
): Parcelable

@Immutable
@Parcelize
data class VideoPictureUI(
    val id: Int,
    val picture: String,
    val nr: Int,
) : Parcelable

@Immutable
@Parcelize
data class SrcUI(
    val original: String = "",
    val large2x: String = "",
    val large: String = "",
    val medium: String = "",
    val small: String = "",
    val portrait: String = "",
    val landscape: String = "",
    val tiny: String = "",
) : Parcelable

@Parcelize
data class UserUI(
    val id: Int,
    val name: String,
    val url: String,
) : Parcelable
