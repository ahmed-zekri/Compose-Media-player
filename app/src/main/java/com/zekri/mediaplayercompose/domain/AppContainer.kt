package com.zekri.mediaplayercompose.domain

import android.media.MediaPlayer

interface AppContainer {
    fun getFileRepository(): FileRepository
    fun getMediaPlayer(): MediaPlayer

}