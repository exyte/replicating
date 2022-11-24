package com.exyte.composesample.nowplaying.header

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.composesample.*
import com.exyte.composesample.R
import com.exyte.composesample.nowplaying.SharedElementParams
import com.exyte.composesample.ui.AnimatedText
import com.exyte.composesample.ui.TopMenu
import com.exyte.composesample.ui.theme.PlayerTheme

/*
 * Created by Exyte on 05.11.2021.
 */
@Stable
@Immutable
class HeaderParams(
    val sharedElementParams: SharedElementParams,
    @DrawableRes val coverId: Int,
    val title: String,
    val author: String,
)

fun Modifier.collapsingHeaderController(
    maxOffsetPx: Float,
    firstVisibleItemIndexProvider: () -> Int,
    onScroll: (currentOffsetY: Float) -> Unit,
): Modifier = composed {
    val scrollListener by rememberUpdatedState(newValue = onScroll)

    val connection = remember {
        object : NestedScrollConnection {
            var lastNotifiedValue = 0f
            var currentOffsetPx = 0f

            fun maybeNotify(value: Float) {
                if (lastNotifiedValue != value) {
                    lastNotifiedValue = value
                    scrollListener(value)
                }
            }

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val firstVisibleIndex = firstVisibleItemIndexProvider()
                currentOffsetPx = (currentOffsetPx + delta).coerceAtMost(0f)


                val isOffsetInAllowedLimits = currentOffsetPx >= -maxOffsetPx

                fun setCurrentOffsetAndNotify() {
                    currentOffsetPx = currentOffsetPx.coerceAtLeast(-maxOffsetPx)
                    maybeNotify(currentOffsetPx)
                }

                fun calculateOffsetAndNotify(): Offset =
                    if (isOffsetInAllowedLimits) {
                        setCurrentOffsetAndNotify()
                        Offset(0f, delta)
                    } else {
                        maybeNotify(currentOffsetPx)
                        Offset.Zero
                    }
                val isScrollingUpWhenHeaderIsDecreased = delta < 0 && firstVisibleIndex == 0
                val isScrollingDownWhenHeaderIsIncreased = delta > 0 && firstVisibleIndex == 0

                return when {
                    isScrollingUpWhenHeaderIsDecreased || isScrollingDownWhenHeaderIsIncreased -> {
                        calculateOffsetAndNotify()
                    }
                    else -> Offset.Zero
                }
            }
        }
    }
    nestedScroll(connection)
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    params: HeaderParams,
    isAppearing: Boolean,
    contentAlphaProvider: State<Float>,
    backgroundColorProvider: State<Color>,
    elevationProvider: () -> Dp = { 0.dp },
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    SharedElementContainer(
        modifier = modifier
            .shadow(elevationProvider())
            .background(backgroundColorProvider.value),
        params = params.sharedElementParams,
        isForward = isAppearing,
        title = {
            HeaderTitle(
                alphaProvider = contentAlphaProvider,
                onBackClick = onBackClick,
                onShareClick = onShareClick
            )
        },
        labels = {
            Labels(
                title = params.title,
                author = params.author,
                useAnimation = isAppearing,
                alphaProvider = contentAlphaProvider,
            )
        },
        sharedElement = {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f),
                painter = painterResource(id = params.coverId),
                contentDescription = "",
            )
        }
    )
}

@Composable
private fun BoxScope.HeaderTitle(
    alphaProvider: State<Float>,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    TopMenu(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .graphicsLayer { alpha = alphaProvider.value }
            .align(Alignment.TopCenter)
            .statusBarsPaddingWithOffset(),
        title = TOP_MENU_TITLE,
        iconsTint = MaterialTheme.colors.onSurface,
        endIconResId = R.drawable.ic_share,
        onStartIconClick = onBackClick,
        onEndIconClick = onShareClick
    )
}

@Composable
private fun BoxScope.Labels(
    title: String,
    author: String,
    useAnimation: Boolean,
    alphaProvider: State<Float>,
) {
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedText(
            modifier = Modifier.graphicsLayer { alpha = alphaProvider.value },
            text = title,
            useAnimation = useAnimation,
            animationDelay = 350L,
            style = MaterialTheme.typography.h4,
            textColor = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = author,
            modifier = Modifier.graphicsLayer { alpha = alphaProvider.value },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
        )
    }
}

const val TOP_MENU_TITLE = "Now Playing"

@Preview
@Composable
fun PreviewCollapsingHeader() {
    val params = HeaderParams(
        sharedElementParams = SharedElementParams(
            initialOffset = Offset.Zero,
            targetOffset = Offset(230f, 20f),
            initialSize = 0.dp,
            targetSize = 220.dp,
            initialCornerRadius = 0.dp,
            targetCornerRadius = 110.dp,
        ),
        coverId = R.drawable.img_album_01,
        title = "It Happened Quiet",
        author = "Aurora",
    )
    PlayerTheme(darkTheme = false) {
        CompositionLocalProvider(LocalInspectionMode provides true) {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                params = params,
                isAppearing = false,
                contentAlphaProvider = remember { mutableStateOf(1f) },
                elevationProvider = { 0.dp },
                backgroundColorProvider = remember { mutableStateOf(Color.White) }
            )
        }
    }
}