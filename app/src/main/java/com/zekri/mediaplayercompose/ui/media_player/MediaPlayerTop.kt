package com.zekri.mediaplayercompose.ui.media_player

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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