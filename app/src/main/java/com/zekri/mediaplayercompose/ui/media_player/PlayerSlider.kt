package com.zekri.mediaplayercompose.ui.media_player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zekri.mediaplayercompose.common.formatMilliSecond

@Composable
fun PlayerSlider(
    mediaPlayerViewModel: MediaPlayerViewModel, playerPosition: Float
) {


    Column(Modifier.fillMaxWidth()) {
        Slider(value = playerPosition, onValueChange = { })
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = ""
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(mediaPlayerViewModel.getMediaDuration().toLong().formatMilliSecond())
        }
    }

}