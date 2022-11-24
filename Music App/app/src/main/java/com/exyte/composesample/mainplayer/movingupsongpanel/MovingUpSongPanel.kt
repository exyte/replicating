package com.exyte.composesample.mainplayer.movingupsongpanel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import com.exyte.composesample.states.PlayerScreenState

/*
 * Created by Exyte on 18.07.2022.
 */

@Composable
fun MovingUpSongPanel(
    modifier: Modifier,
    screenState: PlayerScreenState,
    content: @Composable () -> Unit,
){
    Layout(content, modifier) { measurables, constraints ->
        layout(constraints.maxWidth, constraints.maxHeight) {

            val playerControlConstraints = constraints.copy(
                minHeight = screenState.playerContainerHeight,
                maxHeight = screenState.playerContainerHeight
            )

            val songInfoContainerConstraints = constraints.copy(
                minHeight = screenState.songInfoContainerHeight,
                maxHeight = screenState.songInfoContainerHeight,
            )

            require(measurables.size == 2)

            val playerControlContainer = measurables[1]
            val songInfoContainer = measurables[0]

            songInfoContainer.measure(songInfoContainerConstraints).place(0, screenState.songInfoOffset.toInt())
            playerControlContainer.measure(playerControlConstraints).place(0, screenState.playerControlOffset.toInt())
        }
    }
}