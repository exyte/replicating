package com.exyte.composesample.nowplaying

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.exyte.composesample.*
import com.exyte.composesample.nowplaying.header.Header
import com.exyte.composesample.nowplaying.header.HeaderParams
import com.exyte.composesample.nowplaying.header.collapsingHeaderController
import com.exyte.composesample.ui.theme.PlayerTheme
import kotlinx.coroutines.launch

/*
 * Created by Exyte on 16.10.2021.
 */
data class SharedElementParams(
    val initialOffset: Offset,
    val targetOffset: Offset,
    val initialSize: Dp,
    val targetSize: Dp,
    val initialCornerRadius: Dp,
    val targetCornerRadius: Dp,
)

class CollapsingHeaderState(topInset: Dp) {
    val maxHeaderCollapse = MAX_HEADER_COLLAPSE
    val headerMaxHeight = topInset + MAX_HEADER_HEIGHT
    var headerHeight: Dp by mutableStateOf(headerMaxHeight)

    private val headerElevation by derivedStateOf {
        if (headerHeight > headerMaxHeight - MAX_HEADER_COLLAPSE) 0.dp else 2.dp
    }

    fun findHeaderElevation(isSharedProgressRunning: Boolean): Dp =
        if (isSharedProgressRunning) 0.dp else headerElevation

    companion object {
        val MAX_HEADER_COLLAPSE = 120.dp
        val MAX_HEADER_HEIGHT = 450.dp
    }
}

@Composable
@Stable
fun rememberCollapsingHeaderState(
    key: Any,
    topInset: Dp
) = remember(key1 = key) {
    CollapsingHeaderState(topInset = topInset)
}

@Composable
fun NowPlayingAlbumScreen(
    maxContentWidth: Int,
    sharedElementParams: SharedElementData,
    transitioned: Boolean,
    topInset: Dp,
    onTransitionFinished: () -> Unit,
    onBackClick: () -> Unit,
) {
    val sharedElementTargetSize = 230.dp
    val insets = WindowInsets.navigationBars
    val density = LocalDensity.current
    val bottomInset by remember(insets) {
        derivedStateOf { insets.getBottom(density).toDp(density) }
    }

    NowPlayingAlbumScreen(
        albumInfo = sharedElementParams.albumInfo,
        isAppearing = transitioned,
        sharedElementParams = SharedElementParams(
            initialOffset = Offset(
                sharedElementParams.offsetX.toPx(LocalDensity.current).toFloat(),
                sharedElementParams.offsetY.toPx(LocalDensity.current).toFloat(),
            ),
            targetOffset = Offset(
                x = (maxContentWidth - sharedElementTargetSize.toPx(LocalDensity.current)) / 2f,
                y = 50.dp.toPx(LocalDensity.current).toFloat()
            ),
            initialSize = sharedElementParams.size,
            targetSize = sharedElementTargetSize,
            initialCornerRadius = 10.dp,
            targetCornerRadius = sharedElementTargetSize / 2
        ),
        onBackClick = onBackClick,
        onTransitionFinished = onTransitionFinished,
        insets = DpInsets.from(
            topInset = topInset,
            bottomInset = bottomInset
        )
    )
}

@Composable
fun NowPlayingAlbumScreen(
    modifier: Modifier = Modifier,
    albumInfo: ModelAlbumInfo,
    sharedElementParams: SharedElementParams,
    isAppearing: Boolean,
    insets: DpInsets = DpInsets.Zero,
    onTransitionFinished: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    val density = LocalDensity.current
    val sharedElementProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val titleProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val bgColorProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val listProgress = remember { Animatable(if (isAppearing) 0f else 1f) }

    var likedIndices by remember { mutableStateOf(LikedIndices()) }
    val headerParams = remember {
        HeaderParams(
            sharedElementParams = sharedElementParams,
            coverId = albumInfo.cover,
            title = albumInfo.title,
            author = albumInfo.author
        )
    }

    val headerState =
        rememberCollapsingHeaderState(key = insets.topInset, topInset = insets.topInset)
    val scrollState = rememberLazyListState()

    val bottomControlsHeight by remember(insets) {
        derivedStateOf { 90.dp + insets.bottomInset }
    }

    LaunchedEffect(key1 = isAppearing, block = {
        launch {
            sharedElementProgress.animateTo(
                if (isAppearing) 1f else 0f,
                animationSpec = tween(ANIM_DURATION)
            )
            onTransitionFinished()
        }
        launch {
            titleProgress.animateTo(
                if (isAppearing) 1f else 0f,
                animationSpec = tween(
                    durationMillis = ANIM_DURATION / 2,
                    delayMillis = if (isAppearing) ANIM_DURATION / 2 else 0
                )
            )
        }
        launch {
            listProgress.animateTo(
                if (isAppearing) 1f else 0f,
                animationSpec = tween(
                    durationMillis = ANIM_DURATION / 2,
                    delayMillis = if (isAppearing) ANIM_DURATION / 2 else 0
                )
            )
        }
        launch {
            bgColorProgress.animateTo(
                if (isAppearing) 1f else 0f,
                animationSpec = tween(
                    durationMillis = ANIM_DURATION,
                )
            )
        }
    })

    val surfaceMaterialColor = MaterialTheme.colors.surface
    val surfaceMaterialColorTransparent = surfaceMaterialColor.copy(alpha = 0f)
    val surfaceColor = remember {
        derivedStateOf {
            lerp(
                surfaceMaterialColorTransparent,
                surfaceMaterialColor,
                bgColorProgress.value
            )
        }
    }

    val listOffsetProgressState = sharedElementProgress.asState()
    val contentAlphaState = titleProgress.asState()

    val headerElevationProvider: () -> Dp = remember {
        { headerState.findHeaderElevation(sharedElementProgress.isRunning) }
    }

    val songsListOffsetProvider: Density.() -> IntOffset = remember {
        {
            val y = lerp(
                400.dp,
                0.dp,
                sharedElementProgress.value
            ).toPx(density)
            IntOffset(x = 0, y = y)
        }
    }

    //Remember this offset provider to prevent 'BottomPlayerControls' recompositions
    val controlsOffsetProvider: Density.() -> IntOffset = remember {
        {
            val y = lerp(
                bottomControlsHeight,
                0.dp,
                sharedElementProgress.value
            ).toPx(density)
            IntOffset(x = 0, y = y)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor.value,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerState.headerHeight),
                params = headerParams,
                contentAlphaProvider = contentAlphaState,
                elevationProvider = headerElevationProvider,
                backgroundColorProvider = surfaceColor,
                isAppearing = isAppearing,
                onBackClick = onBackClick,
                onShareClick = onShareClick,
            )

            SongList(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(songsListOffsetProvider)
                    .graphicsLayer { alpha = contentAlphaState.value }
                    .collapsingHeaderController(
                        maxOffsetPx = headerState.maxHeaderCollapse.toPxf(),
                        firstVisibleItemIndexProvider = { scrollState.firstVisibleItemIndex },
                    ) { currentScroll ->
                        headerState.headerHeight =
                            headerState.headerMaxHeight + currentScroll.toDp(density)
                    },
                items = albumInfo.songs,
                bottomPadding = bottomControlsHeight + 24.dp,
                scrollState = scrollState,
                offsetPercent = listOffsetProgressState,
                likedIndices = likedIndices,
                onLikeClick = { clickedIndex ->
                    likedIndices = likedIndices.onAction(clickedIndex)
                }
            )
        }

        BottomPlayerControls(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomControlsHeight)
                .wrapContentSize(align = Alignment.BottomCenter)
                .offset(controlsOffsetProvider),
            bottomPadding = insets.bottomInset,
        )
    }
}


@Preview
@Composable
private fun PreviewNowPlaying() {
    PlayerTheme(false) {
        CompositionLocalProvider(LocalInspectionMode provides true) {
            NowPlayingAlbumScreen(
                modifier = Modifier.width(360.dp),
                albumInfo = PlaybackData().albums.first(),
                isAppearing = false,
                sharedElementParams = SharedElementParams(
                    initialOffset = Offset.Zero,
                    targetOffset = Offset(230f, 100f),
                    initialSize = 0.dp,
                    targetSize = 220.dp,
                    initialCornerRadius = 0.dp,
                    targetCornerRadius = 110.dp,
                )
            )
        }
    }
}