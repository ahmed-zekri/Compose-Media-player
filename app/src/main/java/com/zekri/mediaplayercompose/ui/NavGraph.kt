package com.zekri.mediaplayercompose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zekri.mediaplayercompose.domain.AppContainer
import com.zekri.mediaplayercompose.ui.file_browser.TabLayout
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
            TabLayout(
                modifier = modifier,
                navHostController = navHostController, appContainer = appContainer
            )
        }
        composable(Routes.MEDIA_PLAYER) {
            MediaPlayerContent(modifier, navHostController,appContainer)
        }
    }
}
