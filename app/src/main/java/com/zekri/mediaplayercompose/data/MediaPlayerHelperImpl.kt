package com.zekri.mediaplayercompose.data

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.core.net.toUri
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
    override fun setAudioTrack(context: Context, file: File, play: Boolean): File =
        file.apply {
            player.setDataSource(context, toUri())
            player.prepare()
            if (play)
                player.start()
        }

    override fun getCurrentRelativePositionAsFlow(): Flow<Float> = flow {
        while (true) {
            delay(REFRESH_TIME)
            if (player.isPlaying)
                emit(getTrackRelativePosition())
        }
    }

    override fun getCurrentPosition(): Int = player.currentPosition
    override fun start() = player.start()
    override fun pause() = player.pause()
    override fun stop() = player.stop()
    override fun reset() = player.reset()
    override fun isPlaying() = player.isPlaying
    override fun goToPosition(positionMs: Int) = player.seekTo(positionMs)
}