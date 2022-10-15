package com.zekri.mediaplayercompose

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zekri.mediaplayercompose.ui.NavGraph
import com.zekri.mediaplayercompose.ui.theme.MediaPlayerComposeTheme
import com.zekri.mediaplayercompose.ui.utils.verticalGradientScrim

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher. You can use either a val, as shown in this snippet,
// or a lateinit var in your onAttach() or onCreate() method.
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    listBrowserContent()
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    setContent { Text(text = "Please allow the permission") }
                }
            }
        when {
            checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                listBrowserContent()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                setContent { Text(text = "Please allow the permission") }
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun listBrowserContent() {
        setContent {
            MediaPlayerComposeTheme {
                // A surface container using the 'background' color from the theme
                NavGraph(
                    navHostController = rememberNavController(),
                    appContainer = (applicationContext as App).container,
                    Modifier.verticalGradientScrim(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.50f),
                        startYPercentage = 1f,
                        endYPercentage = 0f
                    )
                )
            }
        }
    }
}

