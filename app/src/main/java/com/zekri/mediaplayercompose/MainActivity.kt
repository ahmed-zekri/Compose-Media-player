package com.zekri.mediaplayercompose


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.zekri.mediaplayercompose.ui.NavGraph
import com.zekri.mediaplayercompose.ui.theme.MediaPlayerComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requiredPermissions = arrayOf(READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, requiredPermissions, 0)
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                setContent {
                    MediaPlayerComposeTheme {
                        // A surface container using the 'background' color from the theme

                        NavGraph(
                            navHostController = rememberNavController(),
                            appContainer = (applicationContext as App).container
                        )

                    }
                }
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                setContent { Text(text = "No permission granted") }
            }
        }

    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MediaPlayerComposeTheme {
        Greeting("Android")
    }
}