package com.exyte.composesample.mainplayer.album

import android.graphics.Canvas
import android.graphics.Typeface
import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exyte.composesample.R
import com.exyte.composesample.states.PlayerScreenState
import com.exyte.composesample.states.Screen
import com.exyte.composesample.ui.theme.Haiti
import com.exyte.composesample.ui.theme.PlayerTheme
import kotlin.math.max

/*
 * Created by Exyte on 18.07.2022.
 */

@Composable
fun Background(
    modifier: Modifier = Modifier,
    screenState: PlayerScreenState,
) {
    if (screenState.currentScreen != Screen.Comments) {
        AlbumInfoContainer(
            modifier = modifier
                .height(screenState.albumsInfoSize),
            photoScale = screenState.photoScale,
        )
    }
}

@Composable
fun AlbumInfoContainer(
    modifier: Modifier,
    photoScale: Float = 1f,
    albumNumbers: Int = 12,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface,
    ) {
        Row {
            AlbumNumberContainerCustom(
                modifier = Modifier.weight(1f)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawIntoCanvas {
                        drawLargeAlbumNumber(it.nativeCanvas, albumNumbers, this.size)
                        drawSmallAlbumNumber(it.nativeCanvas, albumNumbers, this.size)
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_layers),
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .size(24.dp),
                )
                Text(
                    text = AlbumsTitle,
                    fontSize = 14.sp,
                    modifier = Modifier,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.primary,
                )
            }

            Image(
                painter = painterResource(id = R.drawable.img_photo),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = PhotoScale(photoScale)
            )
        }
    }
}

const val AlbumsTitle = "Albums"
const val StartOffsetProportion = 0.3f
const val TopOffsetProportion = 0.7f
const val SmallAlbumNumberAdditionalOffset = 0.01f
const val SmallAlbumNumberTextDivider = 6
const val LargeAlbumNumberTextDivider = 2

fun drawLargeAlbumNumber(canvas: Canvas, albumNumbers: Int, size: Size) {
    val paint = TextPaint()
    paint.textSize = (size.height / LargeAlbumNumberTextDivider)
    paint.color = Haiti.copy(alpha = 0.07f).toArgb()
    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    canvas.drawText(
        albumNumbers.toString(),
        size.width * StartOffsetProportion,
        size.height * TopOffsetProportion,
        paint,
    )
}

fun drawSmallAlbumNumber(canvas: Canvas, albumNumbers: Int, size: Size) {
    val paint = TextPaint()
    paint.textSize = size.height / SmallAlbumNumberTextDivider
    paint.color = Haiti.toArgb()
    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    canvas.drawText(
        albumNumbers.toString(),
        size.width * StartOffsetProportion + SmallAlbumNumberAdditionalOffset,
        size.height * TopOffsetProportion + SmallAlbumNumberAdditionalOffset,
        paint,
    )
}

@Preview
@Composable
private fun PreviewAlbumsInfo() {
    PlayerTheme(darkTheme = false) {
        AlbumInfoContainer(modifier = Modifier.wrapContentHeight(), photoScale = 1f)
    }
}

data class PhotoScale(val additionalScale: Float) : ContentScale {
    override fun computeScaleFactor(srcSize: Size, dstSize: Size): ScaleFactor {
        if (additionalScale > 1f) {
            val newWidth = dstSize.width * additionalScale
            val newHeight = dstSize.height * additionalScale
            return computeFillMaxDimension(srcSize, Size(newWidth, newHeight)).let {
                ScaleFactor(it, it)
            }
        }

        return ContentScale.Crop.computeScaleFactor(srcSize, dstSize)
    }

    private fun computeFillMaxDimension(srcSize: Size, dstSize: Size): Float {
        val widthScale = computeFillWidth(srcSize, dstSize)
        val heightScale = computeFillHeight(srcSize, dstSize)
        return max(widthScale, heightScale)
    }

    private fun computeFillWidth(srcSize: Size, dstSize: Size): Float =
        dstSize.width / srcSize.width

    private fun computeFillHeight(srcSize: Size, dstSize: Size): Float =
        dstSize.height / srcSize.height

}