package com.exyte.composesample.states

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.exyte.composesample.*
import com.exyte.composesample.LocalInspectionMode
import com.exyte.composesample.lerpF
import com.exyte.composesample.toPxf

/*
 * Created by Exyte on 6.7.2022.
 */

enum class Screen {
    Player,
    TransitionToComments,
    Comments,
}

enum class ToNowPlaying {
    None,
    Stable,
}

@Composable
fun rememberPlayerScreenState(
    constraints: Constraints,
    density: Density = LocalDensity.current,
    isInPreviewMode: Boolean = LocalInspectionMode.current,
) = remember(constraints) {
    PlayerScreenState(
        constraints,
        density,
        isInPreviewMode
    )
}

class PlayerScreenState(
    constraints: Constraints,
    private val density: Density,
    isInPreviewMode: Boolean = false,
) {
    @Stable
    private fun Float.toDp() = this.toDp(density)

    var maxContentWidth = constraints.maxWidth
    var maxContentHeight = constraints.maxHeight

    private val easing = FastOutLinearInEasing

    var currentScreen by mutableStateOf(Screen.Player)

    var currentDragOffset by mutableStateOf(0f)

    val songInfoContainerHeight = (maxContentHeight * PlayerControlRatio).toInt()

    val playerContainerHeight = (maxContentHeight * SongInfoContainerRatio).toInt()

    val songInfoContentHeight = songInfoContainerHeight - playerContainerHeight

    val albumContainerHeight = (maxContentHeight * AlbumContainerRatio).toInt()

    val albumImageWidth =
        min((maxContentWidth * 0.35f).toDp(), (maxContentHeight * 0.16f).toDp())

    val commentsContainerHeight = maxContentHeight - albumContainerHeight

    val backHandlerEnabled by derivedStateOf { currentScreen != Screen.Player }

    val fromPlayerControlsToAlbumsListProgress by derivedStateOf {
        if (isInPreviewMode) {
            0f
        } else {
            currentDragOffset / maxContentWidth
        }
    }

    val playerControlOffset by derivedStateOf {
        val cubicBezierEasing = CubicBezierEasing(
            a = 0.25f,
            b = -songInfoContainerHeight.toFloat() / 5,
            c = 0.5f,
            d = -10.dp.toPxf(density)
        )
        cubicBezierEasing.transform(fromPlayerControlsToAlbumsListProgress) + songInfoOffset
    }

    val commentsListOffset by derivedStateOf {
        -(maxContentWidth * (1f - fromPlayerControlsToAlbumsListProgress)).toInt()
    }

    val songInfoOffset by derivedStateOf {
        -(songInfoContainerHeight * fromPlayerControlsToAlbumsListProgress)
    }

    val albumsInfoSize = (maxContentHeight * AlbumInfoRatio).toDp()
    val photoScale by derivedStateOf {
        easing.transform(
            lerpF(
                StartAlbumPhotoScale,
                StopAlbumPhotoScale,
                fromPlayerControlsToAlbumsListProgress
            )
        )
    }
}

const val SongInfoContainerRatio = 0.5f
const val PlayerControlRatio = 0.75f

const val AlbumInfoRatio = 0.4f
const val StartAlbumPhotoScale = 1f
const val StopAlbumPhotoScale = 1.3f

const val AlbumContainerRatio = 0.45f