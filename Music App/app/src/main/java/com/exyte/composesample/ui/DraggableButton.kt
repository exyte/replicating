package com.exyte.composesample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exyte.composesample.R
import com.exyte.composesample.ui.theme.PlayerTheme

/*
 * Created by Exyte on 19.10.2021.
 */

@Composable
fun DraggableButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onPointerDown: () -> Unit = {},
    onDragStart: () -> Unit = {},
    onOffsetChange: (Float) -> Unit = {},
    onDragEnd: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
            )
            .background(
                color = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
            )
            .clickable { onClick() }
            .padding(end = 16.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { onDragStart() },
                    onDragEnd = onDragEnd,
                    onPointerDown = onPointerDown,
                ) { change, dragAmount ->
                    change.consume()
                    onOffsetChange(dragAmount)
                }
            },
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "",
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
@Preview
private fun PreviewDraggableButton() {
    PlayerTheme(darkTheme = false) {
        DraggableButton(
            modifier = Modifier.size(width = 100.dp, height = 64.dp)
        )
    }
}

suspend fun PointerInputScope.detectHorizontalDragGestures(
    onPointerDown: () -> Unit = {},
    onDragStart: (Offset) -> Unit = { },
    onDragEnd: () -> Unit = { },
    onDragCancel: () -> Unit = { },
    onHorizontalDrag: (change: PointerInputChange, dragAmount: Float) -> Unit,
) {
    awaitEachGesture {
        val down = awaitFirstDown(requireUnconsumed = false)
        onPointerDown()
        var overSlop = 0f
        val drag = awaitHorizontalTouchSlopOrCancellation(
            down.id,
        ) { change, over ->
            change.consume()
            overSlop = over
        }
        if (drag != null) {
            onDragStart.invoke(drag.position)
            onHorizontalDrag(drag, overSlop)
            if (
                horizontalDrag(drag.id) {
                    onHorizontalDrag(it, it.positionChange().x)
                    it.consume()
                }
            ) {
                onDragEnd()
            } else {
                onDragCancel()
            }
        }
    }
}