package com.zekri.mediaplayercompose.ui.media_player

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.io.File


class MediaPlayerViewModel(savedStateHandle: SavedStateHandle?) : ViewModel() {
    val fileState: MutableState<File?> = mutableStateOf(null)

    init {
        fileState.value = savedStateHandle?.get("audio")

    }

}