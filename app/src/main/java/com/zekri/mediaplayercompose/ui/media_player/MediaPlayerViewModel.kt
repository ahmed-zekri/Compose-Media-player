package com.zekri.mediaplayercompose.ui.media_player

import android.app.Application
import android.media.MediaPlayer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.zekri.mediaplayercompose.common.playAudioFile
import java.io.File
import java.lang.ref.WeakReference


class MediaPlayerViewModel(savedStateHandle: SavedStateHandle?, application: Application) :
    AndroidViewModel(application) {
    val fileState: MutableState<File?> = mutableStateOf(null)
    var mediaPlayer: WeakReference<MediaPlayer?>

    init {
        fileState.value = savedStateHandle?.get("audio")
        mediaPlayer = WeakReference(fileState.value?.playAudioFile(application))


    }

    override fun onCleared() {
        super.onCleared()

    }

}