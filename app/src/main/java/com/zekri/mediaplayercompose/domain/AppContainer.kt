package com.zekri.mediaplayercompose.domain

interface AppContainer {
    val fileRepository: FileRepository
    val playerHelper: MediaPlayerHelper
}