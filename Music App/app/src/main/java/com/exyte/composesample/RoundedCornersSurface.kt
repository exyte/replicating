package com.exyte.composesample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.composesample.ui.theme.PlayerTheme

/*
 * Created by Exyte on 15.10.2021.
 */
const val CORNERS_SIZE = 48

@Composable
fun RoundedCornersSurface(
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    elevation: Dp = 0.dp,
    color: Color = MaterialTheme.colors.surface,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(
                    bottomStart = CORNERS_SIZE.dp,
                    bottomEnd = CORNERS_SIZE.dp
                ),
                clip = true
            )
            .background(color)
            .padding(top = topPadding),
        content = content
    )
}

@Composable
@Preview
private fun PreviewRoundedCornersSurface() {
    PlayerTheme(darkTheme = false) {
        RoundedCornersSurface(
            modifier = Modifier
                .height(148.dp)
                .fillMaxWidth(),
            topPadding = 48.dp,
        ) {

        }
    }
}