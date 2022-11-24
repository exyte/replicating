package com.exyte.composesample.nowplaying

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.composesample.R
import com.exyte.composesample.ui.theme.PlayerTheme

/*
 * Created by Exyte on 17.10.2021.
 */
@Composable
fun BottomPlayerControls(
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 0.dp,
) {
    var playingState by remember { mutableStateOf(false) }

    BottomPlayerControls(
        modifier = modifier,
        bottomPadding = bottomPadding,
        isPlaying = playingState
    ) {
        playingState = !playingState
    }
}

@Composable
fun BottomPlayerControls(
    modifier: Modifier,
    bottomPadding: Dp,
    isPlaying: Boolean,
    onPlayClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp)
            )
            .padding(bottom = bottomPadding),
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, end = 48.dp, top = 12.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SmallButton(iconResId = R.drawable.ic_previous_track)

            IconButton(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colors.secondary),
                onClick = onPlayClick,
            ) {
                Icon(
                    painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                    contentDescription = "",
                )
            }

            SmallButton(iconResId = R.drawable.ic_next_track)
        }
    }
}

@Composable
private fun SmallButton(@DrawableRes iconResId: Int, contentDescription: String = "") {
    IconButton(
        modifier = Modifier.size(32.dp),
        onClick = {}
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}

@Composable
@Preview
private fun PreviewBottomPlayerControls() {
    PlayerTheme(darkTheme = false) {
        BottomPlayerControls(
            modifier = Modifier.height(90.dp)
        )
    }
}