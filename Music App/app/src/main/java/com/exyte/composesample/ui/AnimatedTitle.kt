package com.exyte.composesample.ui

import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.exyte.composesample.LocalInspectionMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.roundToInt
import com.exyte.composesample.R

/*
 * Created by Exyte on 24.10.2021.
 */
private class FontWidthCalculator {
    fun calculate(paint: android.graphics.Paint): Map<Char, Float> {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz "
        val sizeMap = mutableMapOf<Char, Float>()
        val textPaint = TextPaint(paint)
        alphabet.forEach { letter ->
            sizeMap[letter] = textPaint.measureText(letter.toString())
        }
        return sizeMap
    }
}

@Composable
fun AnimatedText(
    modifier: Modifier = Modifier,
    text: String,
    useAnimation: Boolean = false,
    animationDelay: Long = 0L,
    style: TextStyle = MaterialTheme.typography.h5,
    textColor: Color = MaterialTheme.colors.onSurface,
) {
    val isPreviewMode = LocalInspectionMode.current
    val context = LocalContext.current
    val density = LocalDensity.current
    val textPaint = remember {
        Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = with(density) { style.fontSize.value.sp.toPx() }
            color = textColor.toArgb()
            textAlign = android.graphics.Paint.Align.LEFT
            if (!isPreviewMode) {
                typeface = ResourcesCompat.getFont(context, R.font.abril_fatface)
            }
        }
    }
    val textOffset = remember { textPaint.textSize / 3.2f }
    val alphabet = remember { FontWidthCalculator().calculate(textPaint) }

    val initialOffset = remember { with(density) { 12.sp.toPx() } }
    val offsetStep = remember { initialOffset / 4f }
    var currentOffset by remember { mutableStateOf(initialOffset) }
    var nextOffset by remember { mutableStateOf(initialOffset) }
    var indexAnim by remember { mutableStateOf(0) }

    if (!isPreviewMode && useAnimation) {
        LaunchedEffect(key1 = Unit) {
            launch {
                delay(animationDelay)
                while (indexAnim < text.length) {
                    val shouldSkip = text[indexAnim] == ' '
                    if (shouldSkip) {
                        indexAnim++
                        continue
                    }
                    while (currentOffset > 0f) {
                        delay(16)
                        currentOffset = max(0f, currentOffset - offsetStep)
                        nextOffset = max(0f, nextOffset - (offsetStep * 0.5f))
                    }
                    currentOffset = nextOffset
                    nextOffset = initialOffset
                    indexAnim++
                }
            }
        }
    }
    Layout(
        modifier = modifier,
        content = {
            Canvas(modifier = modifier) {
                val height = size.height
                if (!isPreviewMode && useAnimation) {
                    val lastIndex = indexAnim
                    var x = 0f
                    repeat(lastIndex) { x += alphabet[text[it]] ?: 0f }
                    drawIntoCanvas {
                        if (lastIndex > 0) {
                            textPaint.alpha = 255
                            it.nativeCanvas.drawText(
                                text,
                                0,
                                lastIndex,
                                0f,
                                height / 2f + textOffset,
                                textPaint
                            )
                        }
                        if (lastIndex < text.length) {
                            val firstAnimatedLetterOffsetProgress = if (currentOffset == 0f) {
                                1f
                            } else {
                                1f - currentOffset / initialOffset
                            }
                            textPaint.alpha = (255 * firstAnimatedLetterOffsetProgress).roundToInt()
                            it.nativeCanvas.drawText(
                                text,
                                lastIndex,
                                lastIndex + 1,
                                x,
                                height / 2f + textOffset + currentOffset,
                                textPaint
                            )
                            if (lastIndex + 1 < text.length) {
                                x += (alphabet[text[lastIndex]] ?: 0f)
                                val secondAnimatedLetterOffsetProgress = if (nextOffset == 0f) {
                                    1f
                                } else {
                                    1f - nextOffset / initialOffset
                                }
                                textPaint.alpha =
                                    (255 * secondAnimatedLetterOffsetProgress).roundToInt()
                                it.nativeCanvas.drawText(
                                    text,
                                    lastIndex + 1,
                                    lastIndex + 2,
                                    x,
                                    height / 2f + textOffset + nextOffset,
                                    textPaint
                                )
                            }
                        }
                    }
                } else {
                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            text,
                            0,
                            text.length,
                            0f,
                            height / 2f + textOffset,
                            textPaint
                        )
                    }
                }
            }
        }
    ) { placeables, constraints ->
        val textWidth =
            text.fold(0f) { acc, char -> acc + (alphabet[char] ?: 0f) }.roundToInt() + 1
        val textSize = (style.fontSize.value.sp * 1.2f).roundToPx()
        layout(textWidth, textSize) {
            val canvas = placeables.first()
            val cc = constraints.copy(
                minWidth = textWidth,
                maxWidth = textWidth,
                minHeight = textSize,
                maxHeight = textSize
            )
            canvas.measure(cc).placeRelative(x = 0, y = 0)
        }
    }
}

@Preview
@Composable
fun PreviewText() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        AnimatedText(
            text = "Animated Text",
            useAnimation = false,
        )
    }
}