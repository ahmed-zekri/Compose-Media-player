package com.zekri.mediaplayercompose

import android.app.Application
import com.zekri.mediaplayercompose.data.AppContainerImpl
import com.zekri.mediaplayercompose.domain.AppContainer

class App : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }
}