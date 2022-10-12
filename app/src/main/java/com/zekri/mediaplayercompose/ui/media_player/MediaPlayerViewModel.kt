package com.zekri.mediaplayercompose.ui.media_player

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.zekri.mediaplayercompose.domain.AppContainer
import java.io.File


class MediaPlayerViewModel(
    savedStateHandle: SavedStateHandle?, appContainer: AppContainer, application: Application
) : AndroidViewModel(application) {
    private val _file = mutableStateOf<File?>(savedStateHandle?.get("audio"))
    val file: State<File?> = _file

    private val files: List<File>? = savedStateHandle?.get("audioFiles")
    private val _playerState = mutableStateOf(false)
    val playerState: State<Boolean> = _playerState

    private val mediaPlayerHelper = appContainer.playerHelper

    init {
        playMedia()
    }

    fun setMediaPlayerSource() =
        mediaPlayerHelper.setAudioTrack(getApplication(), file.value!!)

    fun playMedia() = mediaPlayerHelper.start().also { _playerState.value = true }


    fun pauseMedia() = mediaPlayerHelper.pause().also { _playerState.value = false }

    fun stopMedia() = mediaPlayerHelper.stop().also { _playerState.value = false }

    fun getMediaDuration() = mediaPlayerHelper.getTrackDuration()

    fun getMediaRelativePositionAsFlow() = mediaPlayerHelper.getCurrentRelativePositionAsFlow()

    fun isPlaying() = mediaPlayerHelper.isPlaying()

    fun setAdjacentTrack(adjacentTrack: AdjacentTrack) {
        _file.value = files?.run {
            getOrNull(indexOf(file.value) + if (adjacentTrack == AdjacentTrack.NEXT) 1 else -1)?.run {
                mediaPlayerHelper.reset()
                mediaPlayerHelper.setAudioTrack(getApplication(), this)
                mediaPlayerHelper.start()
                this
            }

        }


    }

    enum class AdjacentTrack {
        NEXT, PREVIOUS

    }

    fun seekTo(positionMs: Int) = mediaPlayerHelper.goToPosition(positionMs)
}