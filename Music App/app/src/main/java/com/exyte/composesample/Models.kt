package com.exyte.composesample

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
 * Created by Exyte on 17.10.2021.
 */
@Stable
@Immutable
data class ModelAlbumInfo(
    @DrawableRes val cover: Int,
    val title: String,
    val author: String,
    val year: Int,
    val songs: List<ModelSongInfo>,
)

@Stable
@Immutable
data class ModelComment(
    @DrawableRes val avatarId: Int,
    val name: String,
    val text: String,
    val date: String,
)

@Stable
@Immutable
data class ModelSongInfo(
    val id: Long = 0L,
    val author: String,
    val title: String,
    val duration: String,
)

private fun songs() = listOf(
    ModelSongInfo(0L, "Aurora", "All Is Soft Inside", "3:54"),
    ModelSongInfo(1L, "Aurora", "Queendom", "5:47"),
    ModelSongInfo(2L, "Aurora", "Gentle Earthquakes", "4:32"),
    ModelSongInfo(3L, "Aurora", "Awakening", "6:51"),
    ModelSongInfo(4L, "Aurora", "All Is Soft Inside", "3:54"),
    ModelSongInfo(5L, "Aurora", "Queendom", "5:47"),
    ModelSongInfo(6L, "Aurora", "Gentle Earthquakes", "4:32"),
    ModelSongInfo(7L, "Aurora", "Awakening", "6:51"),
    ModelSongInfo(8L, "Aurora", "All Is Soft Inside", "3:54"),
    ModelSongInfo(9L, "Aurora", "Queendom", "5:47"),
    ModelSongInfo(10L, "Aurora", "Gentle Earthquakes", "4:32"),
    ModelSongInfo(11L, "Aurora", "Awakening", "6:51"),
    ModelSongInfo(12L, "Aurora", "All Is Soft Inside", "3:54"),
    ModelSongInfo(13L, "Aurora", "Queendom", "5:47"),
    ModelSongInfo(14L, "Aurora", "Gentle Earthquakes", "4:32"),
    ModelSongInfo(15L, "Aurora", "Awakening", "6:51"),
)

@Stable
@Immutable
data class PlaybackData(
    val albums: Collection<ModelAlbumInfo> = listOf(
        ModelAlbumInfo(R.drawable.img_album_01, "It happened Quiet", "Aurora", 2018, songs()),
        ModelAlbumInfo(R.drawable.img_album_02, "All My Daemons", "Aurora", 2016, songs()),
        ModelAlbumInfo(R.drawable.img_album_03, "Running", "Aurora", 2015, songs()),
    ),
    val comments: Collection<ModelComment> = listOf(
        ModelComment(
            avatarId = R.drawable.img_face_01,
            name = "Wayne Valentine",
            text = "Unusual beauty",
            date = "Feb 21"
        ),
        ModelComment(
            avatarId = R.drawable.img_face_02,
            name = "Lydia Lopez",
            text = "Love it",
            date = "Feb 8"
        ),
        ModelComment(
            avatarId = R.drawable.img_face_03,
            name = "Allie Weisman",
            text = "This is a masterpiece",
            date = "Jan 9"
        ),
        ModelComment(
            avatarId = R.drawable.img_face_04,
            name = "Bryan King",
            text = "This is awesome",
            date = "Jan 9"
        ),
        ModelComment(
            avatarId = R.drawable.img_face_05,
            name = "Dorothy Harrington",
            text = "The best",
            date = "Jan 8"
        ),
        ModelComment(
            avatarId = R.drawable.img_face_06,
            name = "Helen Price",
            text = "Love it",
            date = "Jan 8"
        ),
    ),
)

@Stable
@Immutable
data class SharedElementData(
    val albumInfo: ModelAlbumInfo,
    val offsetX: Dp,
    val offsetY: Dp,
    val size: Dp,
) {
    companion object {
        val NONE = SharedElementData(ModelAlbumInfo(0, "", "", 0, listOf()), 0.dp, 0.dp, 0.dp)
    }
}

@Stable
@Immutable
data class NowPlayingSong(
    val Singer: String = "Aurora Aksnes",
    val Description: String = "Norwegian singer/songwriter AURORA works in similar dark pop milieu as artists like Oh Land, Lykke Li.",
    val SongDuration: String = "03:20",
)