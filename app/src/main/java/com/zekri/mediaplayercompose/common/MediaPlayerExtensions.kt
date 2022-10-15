package com.zekri.mediaplayercompose.common

import android.media.MediaPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun MediaPlayer.getPlayTimeAsFlow(): Flow<Int> = flow {
    while (true) {
        delay(1000)
        if (isPlaying)
            emit(currentPosition)
    }
}