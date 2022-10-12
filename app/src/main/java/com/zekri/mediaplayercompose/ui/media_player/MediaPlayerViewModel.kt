package com.zekri.mediaplayercompose.ui.media_player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.zekri.mediaplayercompose.domain.AppContainer
import java.io.File


class MediaPlayerViewModel(
    savedStateHandle: SavedStateHandle?, appContainer: AppContainer, application: Application
) : AndroidViewModel(application) {
    val file: File? = savedStateHandle?.get("audio")

    private val mediaPlayerHelper = appContainer.playerHelper


    fun setMediaPlayerSource() =
        mediaPlayerHelper.setAudioTrack(getApplication(), file!!)

    fun playMedia() = mediaPlayerHelper.start()


    fun pauseMedia() = mediaPlayerHelper.pause()

    fun stopMedia() = mediaPlayerHelper.stop()

    fun getMediaDuration() = mediaPlayerHelper.getTrackDuration()

    fun getMediaRelativePositionAsFlow() = mediaPlayerHelper.getCurrentRelativePositionAsFlow()

    fun isPlaying()=mediaPlayerHelper.isPlaying()


}