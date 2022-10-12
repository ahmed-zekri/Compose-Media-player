package com.zekri.mediaplayercompose.ui.media_player

import android.app.Application
import android.media.MediaPlayer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import java.io.File


class MediaPlayerViewModel(
    savedStateHandle: SavedStateHandle?,
    val mediaPlayer: MediaPlayer,
    application: Application
) :
    AndroidViewModel(application) {
    val fileState: MutableState<File?> = mutableStateOf(null)
    val _playerPositionState = mutableStateOf(0)
    val playerPositionState: State<Int> = _playerPositionState
    var playerDuration = 0


    init {
        fileState.value = savedStateHandle?.get("audio")

    }

}