package com.zekri.mediaplayercompose

import android.app.Application
import com.zekri.mediaplayercompose.data.AppContainerImpAudio
import com.zekri.mediaplayercompose.data.AppContainerImplImage
import com.zekri.mediaplayercompose.domain.AppContainer

class App : Application() {
    lateinit var containerImage: AppContainer
    lateinit var containerAudio: AppContainer
    override fun onCreate() {
        super.onCreate()
        containerImage = AppContainerImplImage(this)
        containerAudio = AppContainerImpAudio(this)
    }
}