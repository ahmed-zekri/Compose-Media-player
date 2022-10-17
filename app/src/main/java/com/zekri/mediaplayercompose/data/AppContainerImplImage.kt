package com.zekri.mediaplayercompose.data

import android.app.Application
import com.zekri.mediaplayercompose.domain.AppContainer
import com.zekri.mediaplayercompose.domain.MediaPlayerHelper
import com.zekri.mediaplayercompose.domain.PagerRepository

class AppContainerImplImage(
    application: Application,
    override val pagerRepository: PagerRepository = PagerRepositoryImpl(
        FilePagingSource(
            FileRepositoryImpl(application, FileType.IMAGE)
        )
    ),
    override val playerHelper: MediaPlayerHelper = MediaPlayerHelperImpl()
) : AppContainer
