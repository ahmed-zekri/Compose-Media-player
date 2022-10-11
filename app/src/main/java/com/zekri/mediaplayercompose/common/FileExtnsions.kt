package com.zekri.mediaplayercompose.common

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.media.UnsupportedSchemeException
import android.net.Uri
import androidx.core.net.toUri
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
            audioFileType.Duration = formatMilliSecond(toLong())
        }
        extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR)?.apply {
            audioFileType.author = this
        }


    }

    return audioFileType

}

fun formatMilliSecond(milliseconds: Long): String {
    var finalTimerString = ""
    var secondsString = ""

    val hours = (milliseconds / (1000 * 60 * 60)).toInt()
    val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
    val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000)

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

fun File.playAudioFile(context: Context): MediaPlayer {
    if (extension !in AUDIO_TYPES)
        throw UnsupportedSchemeException("Invalid audio file")

    val myUri: Uri = toUri()
    return MediaPlayer().run {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        setDataSource(context, myUri)
        prepare()
        start()
        this
    }


}