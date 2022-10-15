package com.zekri.mediaplayercompose.ui.media_player

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun BroadcastDescription(
    title: String, name: String, titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Text(text = title, style = titleTextStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
    Text(
        text = name, style = MaterialTheme.typography.bodyMedium, maxLines = 1
    )
}