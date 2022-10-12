package com.zekri.mediaplayercompose.ui.media_player

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zekri.mediaplayercompose.common.formatMilliSecond
import com.zekri.mediaplayercompose.ui.Routes


@Composable
fun MediaPlayerContent(
    modifier: Modifier,
    mediaPlayerViewModel: MediaPlayerViewModel,
    navHostController: NavHostController
) {

    val playerRelativePosition = remember {
        mutableStateOf(0f)
    }

    val backCallback = {
        mediaPlayerViewModel.resetMedia()
        navHostController.navigate(Routes.BROWSER)
    }
    BackHandler(enabled = true) {
        backCallback()
    }
    LaunchedEffect(key1 = true) {
        mediaPlayerViewModel.setMediaPlayerSource()
        mediaPlayerViewModel.getMediaRelativePositionAsFlow().collect {
            if (mediaPlayerViewModel.isPlaying())
                playerRelativePosition.value = it
        }

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        MediaPlayerTop(backCallback)
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
                title = mediaPlayerViewModel.file.value?.name ?: "",
                name = mediaPlayerViewModel.getMediaDuration().toLong().formatMilliSecond()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(10f)
            ) {

                PlayerSlider(
                    mediaPlayerViewModel, playerRelativePosition
                )
                PlayerButtons(
                    Modifier.padding(vertical = 8.dp), mediaPlayerViewModel = mediaPlayerViewModel
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}
