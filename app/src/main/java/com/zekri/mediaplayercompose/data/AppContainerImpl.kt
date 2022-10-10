package com.zekri.mediaplayercompose.data

import com.zekri.mediaplayercompose.domain.AppContainer
import com.zekri.mediaplayercompose.domain.FileRepository

class AppContainerImpl : AppContainer {
    override fun getFileRepository(): FileRepository = FileRepositoryImpl()


}