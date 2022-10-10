package com.zekri.mediaplayercompose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.zekri.mediaplayercompose.domain.AppContainer
import com.zekri.mediaplayercompose.ui.download_file_browser.FileBrowser
import com.zekri.mediaplayercompose.ui.download_file_browser.FileBrowserViewModel
import com.zekri.mediaplayercompose.ui.media_player.MediaPlayerContent

object Routes {

    const val BROWSER = "Browser"
    const val MEDIA_PLAYER = "MediaPlayer"

}


@Composable
fun NavGraph(
    navHostController: NavHostController, appContainer: AppContainer, modifier: Modifier
) {
    NavHost(
        navController = navHostController, startDestination = Routes.BROWSER, modifier = modifier
    ) {
        composable(Routes.BROWSER) {
            val fileBrowserViewModel = FileBrowserViewModel(appContainer.getFileRepository())
            FileBrowser(fileBrowserViewModel,modifier)
        }
        composable(Routes.MEDIA_PLAYER) {
            MediaPlayerContent(modifier)
        }

    }

}
