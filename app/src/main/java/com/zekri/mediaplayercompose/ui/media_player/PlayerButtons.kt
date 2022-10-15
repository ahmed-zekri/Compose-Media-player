package com.zekri.mediaplayercompose.ui.media_player

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zekri.mediaplayercompose.common.SKIP_STEP

enum class SkipDirection {
    FORWARD, BACKWARD
}
@Composable
fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp,
    mediaPlayerViewModel: MediaPlayerViewModel,
    playerPosition: MutableState<Float>
) {
    val skipTo: MediaPlayerViewModel.(SkipDirection) -> Unit = {
        val mediaDuration = getMediaDuration()
        pauseMedia()
        val newPosition: Int =
            if (it == SkipDirection.FORWARD)
                if (getMediaPosition() + SKIP_STEP < mediaDuration) getMediaPosition() + SKIP_STEP
                else mediaDuration
            else
                if (getMediaPosition() - SKIP_STEP > 0) getMediaPosition() - SKIP_STEP
                else 0
        mediaPlayerViewModel.seekTo(newPosition)
        if (mediaDuration != 0)
            playerPosition.value = newPosition.toFloat() / mediaDuration
        mediaPlayerViewModel.playMedia()
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonsModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier.clickable {
                mediaPlayerViewModel.setAdjacentTrack(
                    MediaPlayerViewModel.AdjacentTrack.PREVIOUS
                )
            }
        )
        Image(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier.clickable {
                skipTo(mediaPlayerViewModel, SkipDirection.BACKWARD)
            }
        )
        Image(imageVector = if (!mediaPlayerViewModel.playerState.value) Icons.Rounded.PlayArrow else Icons.Rounded.Pause,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
                .clickable {
                    if (mediaPlayerViewModel.isPlaying()) mediaPlayerViewModel.pauseMedia() else mediaPlayerViewModel.playMedia()
                })
        Image(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier.clickable {
                skipTo(mediaPlayerViewModel, SkipDirection.FORWARD)
            }
        )
        Image(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier.clickable {
                mediaPlayerViewModel.setAdjacentTrack(
                    MediaPlayerViewModel.AdjacentTrack.NEXT
                )
            }
        )
    }
}