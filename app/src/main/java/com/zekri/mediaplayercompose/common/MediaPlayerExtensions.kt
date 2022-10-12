package com.zekri.mediaplayercompose.common

import android.content.Context
import android.media.MediaPlayer
import android.media.UnsupportedSchemeException
import androidx.core.net.toUri
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

fun MediaPlayer.playAudioFile(context: Context, file: File? = null): MediaPlayer {

    file?.apply {
        if (extension !in AUDIO_TYPES)
            throw UnsupportedSchemeException("Invalid audio file")
        setDataSource(context, toUri())
    }

    return also {
        prepare()
        start()
    }
}

fun MediaPlayer.getPlayTimeAsFlow(): Flow<Int> = flow {

    while (true) {

        delay(1000)
        if (isPlaying)

            emit(currentPosition)

    }


}