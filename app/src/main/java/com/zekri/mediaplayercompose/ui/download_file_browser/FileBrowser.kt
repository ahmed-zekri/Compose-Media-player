package com.zekri.mediaplayercompose.ui.download_file_browser

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.zekri.mediaplayercompose.common.Result

@Composable
fun FileBrowser(fileBrowserViewModel: FileBrowserViewModel) {
    val state = fileBrowserViewModel.fileListState


    when (val result = state.value) {

        is Result.Error -> Text(text = result.error ?: "")

        is Result.Success -> Text(text = result.data.toString())
        else -> {}
    }


}