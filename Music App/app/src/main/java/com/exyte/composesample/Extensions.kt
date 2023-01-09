package com.exyte.composesample

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/*
 * Created by Exyte on 17.10.2021.
 */
@Stable
fun Int.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

@Stable
fun Float.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

@Stable
fun Dp.toPx(density: Density): Int = with(density) { this@toPx.roundToPx() }

@Stable
fun Dp.toPxf(density: Density): Float = with(density) { this@toPxf.toPx() }

@Stable
@Composable
fun Dp.toPx(): Int = toPx(LocalDensity.current)

@Stable
@Composable
fun Dp.toPxf(): Float = toPxf(LocalDensity.current)

@Stable
@Composable
fun Float.toDp() = this.toDp(LocalDensity.current)

@Stable
@Composable
fun Int.toDp() = this.toDp(LocalDensity.current)

@Stable
val <E> Collection<E>.lastIndex
    get() = count() - 1

@Stable
fun lerpF(start: Float, stop: Float, fraction: Float): Float =
    (1 - fraction) * start + fraction * stop

@Stable
fun lerp(start: Dp, stop: Dp, fraction: Float): Dp =
    androidx.compose.ui.unit.lerp(start, stop, fraction)

@Stable
fun lerp(start: Color, stop: Color, fraction: Float): Color =
    androidx.compose.ui.graphics.lerp(start, stop, fraction)

/*
    DpSize is inline class here.
    Use this wrapper to 'inline' offsets too.
 */
@JvmInline
@Immutable
value class DpInsets private constructor(private val dpSize: DpSize) {
    @Stable
    val topInset: Dp
        get() = dpSize.width

    @Stable
    val bottomInset: Dp
        get() = dpSize.height


    companion object {
        @Stable
        fun from(topInset: Dp, bottomInset: Dp) = DpInsets(DpSize(topInset, bottomInset))

        @Stable
        val Zero: DpInsets get() = DpInsets(DpSize.Zero)
    }
}

internal val defaultStatusBarPadding = 24.dp

fun Modifier.statusBarsPaddingWithOffset(additionalOffset: Dp = defaultStatusBarPadding): Modifier =
    this
        .padding(top = additionalOffset)
        .statusBarsPadding()

fun Modifier.navigationBarsPaddingWithOffset(additionalOffset: Dp = 0.dp): Modifier =
    this
        .padding(bottom = additionalOffset)
        .navigationBarsPadding()