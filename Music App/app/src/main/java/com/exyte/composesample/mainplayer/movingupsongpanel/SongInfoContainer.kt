package com.exyte.composesample.mainplayer.movingupsongpanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exyte.composesample.R
import com.exyte.composesample.RoundedCornersSurface
import com.exyte.composesample.ui.theme.PlayerTheme

/*
 * Created by Exyte on 08.10.2021.
 */
@Composable
fun SongInfoContainer(
    modifier: Modifier = Modifier,
    height: Dp,
    topPadding: Dp = 0.dp,
) {
    RoundedCornersSurface(
        modifier = modifier,
        color = MaterialTheme.colors.secondary,
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(height),
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f),
            ) {
                SimpleInfoItem(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .padding(top = topPadding),
                    counter = 3847,
                    label = FOLLOWERS,
                )

                Divider(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.1f),
                    thickness = 1.dp,
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )

                SimpleInfoItem(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .padding(top = topPadding),
                    counter = 47,
                    label = FOLLOWING,
                )
            }
            Divider(color = MaterialTheme.colors.primary.copy(alpha = 0.1f))

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(1f)
                    .padding(start = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.img_face_01),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp)
                            .clickable { },
                        contentScale = ContentScale.Crop,
                    )
                    Box(modifier = Modifier.offset(x = (-8).dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.img_face_02),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(34.dp)
                                .border(2.dp, color = MaterialTheme.colors.secondary, CircleShape)
                                .clickable { },
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Box(modifier = Modifier.offset(x = (-16).dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { }
                                .background(
                                    color = MaterialTheme.colors.surface,
                                    shape = CircleShape
                                )
                                .size(36.dp)
                                .border(2.dp, color = MaterialTheme.colors.secondary, CircleShape)
                                .padding(6.dp),
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
                        )
                    }
                }

                SimpleInfoItem(
                    modifier = Modifier
                        .weight(1f),
                    counter = 15,
                    label = COMMENTS,
                )
            }
        }
    }
}

@Composable
private fun SimpleInfoItem(modifier: Modifier, counter: Int, label: String) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = counter.toString(),
                color = MaterialTheme.colors.onSecondary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

const val FOLLOWERS = "Followers"
const val FOLLOWING = "Following"
const val COMMENTS = "Comments"

@Preview
@Composable
private fun PreviewSongInfoContainer() {
    PlayerTheme(darkTheme = false) {
        SongInfoContainer(
            modifier = Modifier.fillMaxWidth(),
            height = 300.dp,
        )
    }
}

@Preview
@Composable
private fun PreviewSimpleInfoItem() {
    PlayerTheme(darkTheme = false) {
        SimpleInfoItem(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .background(color = MaterialTheme.colors.secondary),
            counter = 1875,
            label = "Followers"
        )
    }
}