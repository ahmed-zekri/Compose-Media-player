package com.zekri.mediaplayercompose.data

import com.zekri.mediaplayercompose.domain.AppContainer
import com.zekri.mediaplayercompose.domain.FileRepository
import com.zekri.mediaplayercompose.domain.MediaPlayerHelper

class AppContainerImpl(
    override val fileRepository: FileRepository = FileRepositoryImpl(),
    override val playerHelper: MediaPlayerHelper = MediaPlayerHelperImpl()
) : AppContainer
