package com.zekri.mediaplayercompose.domain

interface AppContainer {
    val playerHelper: MediaPlayerHelper
    val pagerRepository: PagerRepository
}