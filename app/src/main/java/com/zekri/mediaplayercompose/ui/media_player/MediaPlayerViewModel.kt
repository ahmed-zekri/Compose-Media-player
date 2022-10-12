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
    val file: File? = savedStateHandle?.get("audio")
    private val _playerStata = mutableStateOf(false)
    val playerState: State<Boolean> = _playerStata

    private val mediaPlayerHelper = appContainer.playerHelper


    fun setMediaPlayerSource() =
        mediaPlayerHelper.setAudioTrack(getApplication(), file!!)

    fun playMedia() = mediaPlayerHelper.start().also { _playerStata.value = true }


    fun pauseMedia() = mediaPlayerHelper.pause().also { _playerStata.value = false }

    fun stopMedia() = mediaPlayerHelper.stop().also { _playerStata.value = false }

    fun getMediaDuration() = mediaPlayerHelper.getTrackDuration()

    fun getMediaRelativePositionAsFlow() = mediaPlayerHelper.getCurrentRelativePositionAsFlow()

    fun isPlaying() = mediaPlayerHelper.isPlaying()


}