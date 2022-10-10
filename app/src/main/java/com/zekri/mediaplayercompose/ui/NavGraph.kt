package com.zekri.mediaplayercompose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController

object Routes {

    const val BROWSER = "Browser"
    const val MEDIA_PLAYER = "MediaPlayer"

}


@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = "", modifier = Modifier
    ) {


    }

}