package com.zekri.mediaplayercompose.common

import android.media.MediaMetadataRetriever
import android.media.UnsupportedSchemeException
import java.io.File

data class AudioFileType(
    var Duration: String? = null,
    var author: String? = null,

    )

fun File.getAudioInfo(): AudioFileType {

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
