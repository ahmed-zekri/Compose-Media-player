package com.zekri.mediaplayercompose.domain

import android.content.Context
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MediaPlayerHelper {
    fun getTrackRelativePosition(): Float
    fun getTrackDuration(): Int
    fun setAudioTrack(context: Context, file: File,play:Boolean=false): File
    fun getCurrentRelativePositionAsFlow(): Flow<Float>
    fun getCurrentPosition(): Int
    fun start()
    fun pause()
    fun stop()
    fun isPlaying(): Boolean
    fun reset()
    fun goToPosition(positionMs: Int)
}