package com.zekri.mediaplayercompose.data

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.UnsupportedSchemeException
import androidx.core.net.toUri
import com.zekri.mediaplayercompose.common.AUDIO_TYPES
import com.zekri.mediaplayercompose.common.REFRESH_TIME
import com.zekri.mediaplayercompose.domain.MediaPlayerHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class MediaPlayerHelperImpl : MediaPlayerHelper {
    private var player: MediaPlayer


    init {
        player = MediaPlayer().also {
            player = it
            it.setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()
            )

        }
    }


    override fun getTrackDuration() = player.run {
        if (isPlaying) duration
        else 0
    }

    override fun getTrackRelativePosition() = player.run { currentPosition.toFloat() / duration }

    override fun setAudioTrack(context: Context, file: File): File = file.apply {
        if (extension !in AUDIO_TYPES) throw UnsupportedSchemeException("Invalid audio file")
        player.setDataSource(context, toUri())
        player.prepare()


    }

    override fun getCurrentRelativePositionAsFlow(): Flow<Float> = flow {

        while (true) {

            delay(REFRESH_TIME)
            if (player.isPlaying)

                emit(getTrackRelativePosition())

        }


    }

    override fun start() = player.start()

    override fun pause() = player.pause()

    override fun stop() = player.stop()
    override fun isPlaying() = player.isPlaying
}