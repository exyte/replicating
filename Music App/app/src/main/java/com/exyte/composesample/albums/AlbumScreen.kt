package com.exyte.composesample.albums

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.composesample.*
import com.exyte.composesample.states.PlayerScreenState

/*
 * Created by Exyte on 18.07.2022.
 */

@Composable
fun AlbumScreen(
    screenState: PlayerScreenState,
    playbackData: PlaybackData,
    sharedProgress: Float,
    onBackClick: () -> Unit,
    onInfoClick: (info: ModelAlbumInfo, offsetX: Float, offsetY: Float, size: Int) -> Unit,
) {
    AlbumScreenCustom(
        modifier = Modifier.background(Color.Transparent),
        screenState = screenState,
        topCommentsPadding = CORNERS_SIZE.dp
    ) {
        AlbumsListContainer(
            albumData = playbackData.albums,
            transitionAnimationProgress = sharedProgress,
            appearingAnimationProgress = screenState.fromPlayerControlsToAlbumsListProgress,
            albumImageWidth = screenState.albumImageWidth,
            onBackClick = onBackClick,
            onInfoClick = onInfoClick
        )
        CommentsList(
            comments = playbackData.comments,
            topPadding = CORNERS_SIZE.dp
        )
    }
}

@Composable
fun AlbumScreenCustom(
    modifier: Modifier,
    screenState: PlayerScreenState,
    topCommentsPadding: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    Layout(content, modifier) { measurables, constraints ->
        layout(constraints.maxWidth, constraints.maxHeight) {

            val albumListContainerConstraints = constraints.copy(
                minHeight = screenState.albumContainerHeight,
                maxHeight = screenState.albumContainerHeight
            )

            val commentsListContainerConstraints = constraints.copy(
                minHeight = screenState.commentsContainerHeight + topCommentsPadding.roundToPx(),
                maxHeight = screenState.commentsContainerHeight + topCommentsPadding.roundToPx(),
            )

            require(measurables.size == 2)

            val albumListContainer = measurables[0]
            val commentsListContainer = measurables[1]

            commentsListContainer.measure(commentsListContainerConstraints).place(
                screenState.commentsListOffset,
                screenState.albumContainerHeight - CORNERS_SIZE.dp.roundToPx(),
            )

            albumListContainer.measure(albumListContainerConstraints)
                .place(0, 0)
        }
    }
}