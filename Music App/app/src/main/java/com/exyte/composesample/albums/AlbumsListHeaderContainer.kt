package com.exyte.composesample.albums

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exyte.composesample.*
import com.exyte.composesample.R
import com.exyte.composesample.ui.TopMenu
import com.exyte.composesample.ui.theme.PlayerTheme

/*
 * Created by Exyte on 11.10.2021.
 */
@Composable
fun AlbumsListContainer(
    modifier: Modifier = Modifier,
    listScrollState: ScrollState = rememberScrollState(),
    albumData: Collection<ModelAlbumInfo>,
    albumImageWidth: Dp = 150.dp,
    transitionAnimationProgress: Float = 0f,
    appearingAnimationProgress: Float = 1f,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onInfoClick: (info: ModelAlbumInfo, offsetX: Float, offsetY: Float, size: Int) -> Unit = { _, _, _, _ -> },
) {
    var clickedItemIndex by remember { mutableStateOf(-1) }
    val transitionInProgress by remember(transitionAnimationProgress) {
        derivedStateOf { transitionAnimationProgress > 0f }
    }

    RoundedCornersSurface(
        modifier = modifier,
        color = MaterialTheme.colors.primary,
    ) {
        Column() {
            TopMenu(
                modifier = Modifier
                    .statusBarsPaddingWithOffset()
                    .padding(horizontal = 16.dp),
                title = TOP_MENU_TITLE,
                endIconResId = R.drawable.ic_share,
                titleColor = MaterialTheme.colors.onPrimary,
                iconsTint = MaterialTheme.colors.onPrimary,
                onStartIconClick = onBackClick,
                onEndIconClick = onShareClick,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.horizontalScroll(listScrollState)
            ) {
                albumData.forEachIndexed { index, info ->
                    Spacer(modifier = Modifier.width(if (index == 0) 24.dp else 16.dp))
                    val itemAlpha =
                        if (clickedItemIndex == index && transitionInProgress) 0f else 1f
                    CompositionLocalProvider(LocalContentAlpha provides itemAlpha) {
                        val topOffset = ((index + 1) * (1f - appearingAnimationProgress) * 10).dp
                        AlbumListItem(
                            modifier = Modifier
                                .offset(y = topOffset)
                                .alpha(appearingAnimationProgress),
                            info = info,
                            albumImageWidth = albumImageWidth,
                            onClick = { clickedInfo, offset, size ->
                                clickedItemIndex = index
                                onInfoClick(clickedInfo, offset.x, offset.y, size)
                            })
                    }
                    if (index == albumData.lastIndex) {
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun AlbumListItem(
    modifier: Modifier = Modifier,
    info: ModelAlbumInfo,
    albumImageWidth: Dp,
    onClick: (info: ModelAlbumInfo, offset: Offset, size: Int) -> Unit,
) {
    var parentOffset by remember { mutableStateOf(Offset.Unspecified) }
    var mySize by remember { mutableStateOf(0) }
    Column(
        modifier = modifier.width(albumImageWidth),
    ) {
        Image(
            painter = painterResource(id = info.cover),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .onGloballyPositioned { coordinates ->
                    parentOffset = coordinates.positionInRoot()
                    mySize = coordinates.size.width
                }
                .clip(RoundedCornerShape(10.dp))
                .alpha(LocalContentAlpha.current)
                .clickable { onClick(info, parentOffset, mySize) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = info.title,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = info.year.toString(),
            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
        )
    }
}

const val TOP_MENU_TITLE = "Albums"

@Composable
@Preview
private fun PreviewAlbumHeader() {
    PlayerTheme(false) {
        AlbumsListContainer(albumData = PlaybackData().albums)
    }
}