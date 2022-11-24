package com.exyte.composesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.compositionLocalOf
import androidx.core.view.WindowCompat
import com.exyte.composesample.ui.theme.PlayerTheme

val LocalInspectionMode= compositionLocalOf { false }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PlayerTheme(darkTheme = false) {
                PlayerScreen(playbackData = PlaybackData())
            }
        }
    }
}
