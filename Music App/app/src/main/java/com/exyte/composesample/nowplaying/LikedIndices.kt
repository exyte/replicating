package com.exyte.composesample.nowplaying

import androidx.compose.runtime.Stable

/*
 * Created by Exyte on 05.11.2021.
 */
@Stable
class LikedIndices(private val indices: MutableSet<Int> = mutableSetOf()) {

    fun onAction(index: Int): LikedIndices {
        if (indices.contains(index)) {
            indices -= index
        } else {
            indices += index
        }
        return LikedIndices(indices)
    }

    fun isLiked(index: Int): Boolean = indices.contains(index)
}
