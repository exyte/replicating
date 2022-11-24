package com.exyte.composesample

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.exyte.composesample.albums.AlbumScreen
import com.exyte.composesample.nowplaying.ANIM_DURATION
import com.exyte.composesample.nowplaying.NowPlayingAlbumScreen
import com.exyte.composesample.mainplayer.*
import com.exyte.composesample.mainplayer.album.Background
import com.exyte.composesample.states.Screen
import com.exyte.composesample.states.ToNowPlaying
import com.exyte.composesample.states.rememberPlayerScreenState
import com.exyte.composesample.ui.theme.PlayerTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import kotlin.math.abs

/*
 * Created by Exyte on 11.10.2021.
 */

@Composable
fun PlayerScreen(playbackData: PlaybackData = PlaybackData()) {
     val systemUiController : SystemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = false
    }
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenState = rememberPlayerScreenState(constraints, LocalDensity.current)

        var toNowPlayingTransition by remember { mutableStateOf(ToNowPlaying.None) }

        val animScope = rememberCoroutineScope()

        var sharedElementTransitioned by remember { mutableStateOf(false) }
        var sharedElementParams by remember { mutableStateOf(SharedElementData.NONE) }

        val titleProgressForward = remember { Animatable(0f) }
        val sharedProgress = titleProgressForward.value

        fun animateOffset(initialValue: Float, targetValue: Float, onEnd: () -> Unit) {
            val distance = abs(targetValue - initialValue)
            val distancePercent = distance / screenState.maxContentWidth
            val duration = (250 * distancePercent).toInt()

            animScope.launch {
                animate(
                    initialValue = initialValue,
                    targetValue = targetValue,
                    animationSpec = tween(duration),
                ) { value, _ -> screenState.currentDragOffset = value }
                onEnd()
            }
        }

        fun collapse() {
            animateOffset(
                initialValue = screenState.currentDragOffset, targetValue = 0f
            ) {
                screenState.currentScreen = Screen.Player
            }
        }

        fun animateTitleProgress(targetValue: Float) {
            animScope.launch {
                titleProgressForward.animateTo(
                    targetValue = targetValue,
                    animationSpec = tween(ANIM_DURATION),
                )
            }
        }

        val goBackFromNowPlayingScreen: () -> Unit = remember {
            {
                sharedElementTransitioned = false
                animateTitleProgress(0f)
            }
        }

        Background(
            modifier = Modifier.align(Alignment.BottomCenter), screenState = screenState
        )

        if (screenState.currentScreen != Screen.Player) {
            val density = LocalDensity.current
            AlbumScreen(
                screenState = screenState,
                playbackData = playbackData,
                sharedProgress = sharedProgress,
                onBackClick = {
                    screenState.currentScreen = Screen.TransitionToComments
                    collapse()
                },
                onInfoClick = { data, x, y, size ->
                    sharedElementParams = SharedElementData(
                        data, x.toDp(density), y.toDp(density), size.toDp(density)
                    )
                    sharedElementTransitioned = true
                    toNowPlayingTransition = ToNowPlaying.Stable
                    animateTitleProgress(1f)
                },
            )
        }

        if (screenState.currentScreen != Screen.Comments) {
            MainPlayerScreen(
                screenState = screenState,
            )
        }

        if (toNowPlayingTransition == ToNowPlaying.Stable) {
            NowPlayingAlbumScreen(
                maxContentWidth = screenState.maxContentWidth,
                sharedElementParams = sharedElementParams,
                transitioned = sharedElementTransitioned,
                topInset = defaultStatusBarPadding,
                onTransitionFinished = {
                    if (!sharedElementTransitioned) {
                        toNowPlayingTransition = ToNowPlaying.None
                    }
                },
                onBackClick = goBackFromNowPlayingScreen
            )
        }

        BackHandler(screenState.backHandlerEnabled) {
            when {
                sharedElementTransitioned -> goBackFromNowPlayingScreen()
                screenState.currentScreen != Screen.Player -> {
                    screenState.currentScreen = Screen.TransitionToComments
                    collapse()
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewMainScreen() {
    PlayerTheme(darkTheme = false) {
        CompositionLocalProvider(LocalInspectionMode provides true) {
            PlayerScreen()
        }
    }
}