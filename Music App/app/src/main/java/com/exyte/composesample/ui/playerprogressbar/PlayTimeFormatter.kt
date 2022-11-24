package com.exyte.composesample.ui.playerprogressbar

import java.util.concurrent.TimeUnit

/*
 * Created by Exyte on 18.07.2022.
 */

internal class PlayTimeFormatter {
    fun format(playbackTimeSeconds: Long): String {
        val minutes = TimeUnit.SECONDS.toMinutes(playbackTimeSeconds)
        val seconds = if (playbackTimeSeconds < 60) {
            playbackTimeSeconds
        } else {
            (playbackTimeSeconds - TimeUnit.MINUTES.toSeconds(minutes))
        }
        return buildString {
            if (minutes < 10) append(0)
            append(minutes)
            append(":")
            if (seconds < 10) append(0)
            append(seconds)
        }
    }
}