package com.zekri.mediaplayercompose.common

import android.media.MediaMetadataRetriever
import java.io.File

data class AudioFileType(
    var duration: String? = null,
    var author: String? = null,
    var date: String? = null
)

fun File.getAudioInfo(): AudioFileType {
    val audioFileType = AudioFileType()

    MediaMetadataRetriever().run {
        setDataSource(absolutePath)
        extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.apply {
            audioFileType.duration = toLong().formatMilliSecond()
        }
        extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR)?.apply {
            audioFileType.author = this
        }
        extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)?.apply {
            audioFileType.date = this
        }
    }

    return audioFileType
}

fun Long.formatMilliSecond(): String {
    val hours = (this / (1000 * 60 * 60)).toInt()
    val minutes = (this % (1000 * 60 * 60)).toInt() / (1000 * 60)
    val seconds = (this % (1000 * 60 * 60) % (1000 * 60) / 1000)
    var finalTimerString = if (hours > 0) "$hours:" else ""
    val secondsString = if (seconds < 10)
        "0$seconds"
    else
        "" + seconds


    finalTimerString = "$finalTimerString$minutes:$secondsString"

    return finalTimerString
}

fun File.isAudioFile() = extension in AUDIO_TYPES
