package com.zekri.mediaplayercompose.domain

import android.content.Context
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MediaPlayerHelper {


    fun getTrackRelativePosition(): Float

    fun getTrackDuration(): Int
    fun setAudioTrack(context: Context, file: File): File

    fun getCurrentRelativePositionAsFlow(): Flow<Float>
    fun start()
    fun pause()
    fun stop()
    fun isPlaying(): Boolean
}