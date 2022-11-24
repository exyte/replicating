package com.exyte.composesample.mainplayer.album

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.exyte.composesample.toPx

/*
 * Created by Exyte on 18.07.2022.
 */

@Composable
fun AlbumNumberContainerCustom(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    val iconLayersSize = 24.dp.toPx()
    Layout(content, modifier) { measurables, constraints ->
        layout(constraints.maxWidth, constraints.maxHeight) {

            val albumNumberConstraints = constraints.copy(
                minHeight = constraints.maxHeight,
                maxHeight = constraints.maxHeight,
            )

            val iconLayersConstraint = constraints.copy(
                minHeight = iconLayersSize,
                maxHeight = iconLayersSize,
                maxWidth = iconLayersSize,
                minWidth = iconLayersSize,
            )

            require(measurables.size == 3)

            val albumNumber = measurables[0]
            val iconLayers = measurables[1]
            val albumText = measurables[2]

            val basicStartOffset = (StartOffsetProportion * constraints.maxWidth).toInt()
            val basicTopOffset = (constraints.maxHeight * TopOffsetProportion).toInt()

            albumNumber.measure(albumNumberConstraints).place(0, 0)
            iconLayers.measure(iconLayersConstraint).place(
                basicStartOffset - iconLayersSize,
                (constraints.maxHeight * 0.54).toInt()
            )
            albumText.measure(constraints).place(
                basicStartOffset + 10.dp.roundToPx(),
                (basicTopOffset + 10.dp.roundToPx())
            )
        }
    }
}