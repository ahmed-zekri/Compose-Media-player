package com.zekri.mediaplayercompose.common

import android.media.MediaMetadataRetriever
import android.media.UnsupportedSchemeException
import java.io.File

data class AudioFileType(
    var Duration: String? = null,
    var author: String? = null,

    )

fun File.getAudioInfo(): AudioFileType {
    if (extension !in AUDIO_TYPES)
        throw UnsupportedSchemeException("Invalid audio file")
    val audioFileType = AudioFileType()

    MediaMetadataRetriever().run {
        setDataSource(absolutePath)
        extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.apply {
            audioFileType.Duration = toLong().formatMilliSecond()
        }
        extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR)?.apply {
            audioFileType.author = this
        }


    }

    return audioFileType

}

fun Long.formatMilliSecond(): String {
    var finalTimerString = ""
    var secondsString = ""

    val hours = (this / (1000 * 60 * 60)).toInt()
    val minutes = (this % (1000 * 60 * 60)).toInt() / (1000 * 60)
    val seconds = (this % (1000 * 60 * 60) % (1000 * 60) / 1000)

    if (hours > 0) {
        finalTimerString = "$hours:"
    }

    secondsString = if (seconds < 10) {
        "0$seconds"
    } else {
        "" + seconds
    }

    finalTimerString = "$finalTimerString$minutes:$secondsString"

    return finalTimerString


}
