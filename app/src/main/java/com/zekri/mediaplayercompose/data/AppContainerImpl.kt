package com.zekri.mediaplayercompose.data

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.zekri.mediaplayercompose.domain.AppContainer
import com.zekri.mediaplayercompose.domain.FileRepository

class AppContainerImpl : AppContainer {
    override fun getFileRepository(): FileRepository = FileRepositoryImpl()
    override fun getMediaPlayer() = MediaPlayer().also {
        it.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }


}