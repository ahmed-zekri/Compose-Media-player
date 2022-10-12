package com.zekri.mediaplayercompose.ui.media_player

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zekri.mediaplayercompose.common.formatMilliSecond
import com.zekri.mediaplayercompose.common.getPlayTimeAsFlow
import com.zekri.mediaplayercompose.common.playAudioFile
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.Duration


@Composable
fun MediaPlayerContent(modifier: Modifier, mediaPlayerViewModel: MediaPlayerViewModel) {
    val audioFile by mediaPlayerViewModel.fileState
    val playerPosition by mediaPlayerViewModel.playerPositionState


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        MediaPlayerTop()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            PlayerImage(
                imageUrl = "https://files.thisamericanlife.org/sites/all/themes/thislife/img/tal-name-1400x1400.png",
                modifier = Modifier.weight(10f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            BroadcastDescription(
                title = audioFile?.name ?: "",
                name = mediaPlayerViewModel.playerDuration.toLong().formatMilliSecond()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(10f)
            ) {
                println(playerPosition.toFloat() / mediaPlayerViewModel.playerDuration)
                PlayerSlider(
                    Duration.ofMinutes(
                        mediaPlayerViewModel.playerDuration.toLong() / 60000,

                        ),
                    if (mediaPlayerViewModel.playerDuration == 0) 0f else playerPosition.toFloat() / mediaPlayerViewModel.playerDuration
                )
                PlayerButtons(
                    Modifier.padding(vertical = 8.dp), mediaPlayerViewModel = mediaPlayerViewModel
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}

@Composable
private fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp,
    mediaPlayerViewModel: MediaPlayerViewModel

) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonsModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        Image(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        Image(imageVector = Icons.Rounded.PlayArrow,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
                .clickable {

                    mediaPlayerViewModel.mediaPlayer
                        .playAudioFile(
                            mediaPlayerViewModel.getApplication(),
                            mediaPlayerViewModel.fileState.value!!
                        )
                        .apply {
                            mediaPlayerViewModel.playerDuration = duration
                            getPlayTimeAsFlow()
                                .onEach {
                                    mediaPlayerViewModel._playerPositionState.value = it

                                }
                                .launchIn(coroutineScope)

                        }

                })
        Image(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        Image(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
    }
}

@Composable
private fun PlayerSlider(episodeDuration: Duration?, playerPosition: Float) {
    if (episodeDuration != null) {
        Column(Modifier.fillMaxWidth()) {
            Slider(value = playerPosition, onValueChange = { })
            Row(Modifier.fillMaxWidth()) {
                Text(text = "0s")
                Spacer(modifier = Modifier.weight(1f))
                Text("${episodeDuration.seconds}s")
            }
        }
    }
}

@Composable
fun BroadcastDescription(
    title: String, name: String, titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Text(text = title, style = titleTextStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
    Text(
        text = name, style = MaterialTheme.typography.bodyMedium, maxLines = 1
    )

}

@Composable
fun MediaPlayerTop() {
    Row(Modifier.fillMaxWidth()) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)

        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)

        }


    }
}


@Composable
fun PlayerImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .sizeIn(maxHeight = 500.dp, maxWidth = 500.dp)
            .aspectRatio(1f)

    )

}