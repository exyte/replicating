package com.exyte.composesample.ui.playerprogressbar

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exyte.composesample.NowPlayingSong
import com.exyte.composesample.R
import com.exyte.composesample.lerpF
import com.exyte.composesample.toPxf
import com.exyte.composesample.ui.theme.PlayerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random

/*
 * Created by Exyte on 08.10.2021.
 */

class ProgressBarState(duration: String) {
    var isPlaying by mutableStateOf(false)
    var elapsedTime by mutableStateOf("00:00")
    var timeLeft by mutableStateOf(duration)
}

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    songDuration: String,
) {
    val dateFormatter = remember { PlayTimeFormatter() }
    val progressBarState = remember { ProgressBarState(songDuration) }
    var currentTime by remember { mutableStateOf(0L) }

    if (progressBarState.isPlaying) {
        LaunchedEffect(key1 = progressBarState.isPlaying) {
            val songTime = 200L
            while (isActive) {
                progressBarState.elapsedTime = dateFormatter.format(currentTime)
                progressBarState.timeLeft = dateFormatter.format(songTime - currentTime)

                delay(1000L)

                currentTime += 1
                if (currentTime > songTime) {
                    currentTime = 0
                }
            }
        }
    }

    ProgressBar(
        modifier = modifier,
        state = progressBarState,
    ) {
        progressBarState.isPlaying = !progressBarState.isPlaying
    }
}

@Composable
fun ProgressBar(
    modifier: Modifier,
    state: ProgressBarState,
    onButtonClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PlayTimeText(text = state.elapsedTime)

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVolumeLevelBar(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(48.dp),
                barWidth = 3.dp,
                gapWidth = 2.dp,
                isAnimating = state.isPlaying,
            )
            PlayPauseButton(isPlaying = state.isPlaying, onClick = onButtonClick)
        }

        PlayTimeText(text = state.timeLeft)
    }
}

@NonRestartableComposable
@Composable
fun PlayTimeText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colors.onPrimary,
        fontSize = 11.sp,
        textAlign = TextAlign.Center,
        maxLines = 1
    )
}

@Composable
fun PlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit = {},
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(32.dp)
            .background(color = MaterialTheme.colors.primaryVariant, shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}

@Composable
fun AnimatedVolumeLevelBar(
    modifier: Modifier = Modifier,
    barWidth: Dp = 2.dp,
    gapWidth: Dp = barWidth,
    barColor: Color = MaterialTheme.colors.onPrimary,
    isAnimating: Boolean = false,
) {
    val infiniteAnimation = rememberInfiniteTransition()
    val animations = mutableListOf<State<Float>>()
    val random = remember { Random(System.currentTimeMillis()) }

    repeat(15) {
        val durationMillis = random.nextInt(500, 2000)
        animations += infiniteAnimation.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis),
                repeatMode = RepeatMode.Reverse,
            )
        )
    }

    val barWidthFloat by rememberUpdatedState(newValue = barWidth.toPxf())
    val gapWidthFloat by rememberUpdatedState(newValue = gapWidth.toPxf())

    val initialMultipliers = remember {
        mutableListOf<Float>().apply {
            repeat(MaxLinesCount) { this += random.nextFloat() }
        }
    }

    val heightDivider by animateFloatAsState(
        targetValue = if (isAnimating) 1f else 6f,
        animationSpec = tween(1000, easing = LinearEasing)
    )

    Canvas(modifier = modifier) {
        val canvasHeight = size.height
        val canvasWidth = size.width
        val canvasCenterY = canvasHeight / 2f

        val count =
            (canvasWidth / (barWidthFloat + gapWidthFloat)).toInt().coerceAtMost(MaxLinesCount)
        val animatedVolumeWidth = count * (barWidthFloat + gapWidthFloat)
        var startOffset = (canvasWidth - animatedVolumeWidth) / 2

        val barMinHeight = 0f
        val barMaxHeight = canvasHeight / 2f / heightDivider

        repeat(count) { index ->
            val currentSize = animations[index % animations.size].value
            var barHeightPercent = initialMultipliers[index] + currentSize
            if (barHeightPercent > 1.0f) {
                val diff = barHeightPercent - 1.0f
                barHeightPercent = 1.0f - diff
            }
            val barHeight = lerpF(barMinHeight, barMaxHeight, barHeightPercent)

            drawLine(
                color = barColor,
                start = Offset(startOffset, canvasCenterY - barHeight / 2),
                end = Offset(startOffset, canvasCenterY + barHeight / 2),
                strokeWidth = barWidthFloat,
                cap = StrokeCap.Round,
            )
            startOffset += barWidthFloat + gapWidthFloat
        }
    }
}

const val MaxLinesCount = 100

@Preview
@Composable
fun ProgressBarPreview() {
    PlayerTheme {
        ProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            state = ProgressBarState(NowPlayingSong().SongDuration),
        )
    }
}

