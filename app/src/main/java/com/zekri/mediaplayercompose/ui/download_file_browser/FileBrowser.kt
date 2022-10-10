package com.zekri.mediaplayercompose.ui.download_file_browser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zekri.mediaplayercompose.common.Result
import com.zekri.mediaplayercompose.ui.theme.Typography
import java.io.File

@Composable
fun FileBrowser(fileBrowserViewModel: FileBrowserViewModel, modifier: Modifier) {
    val state = fileBrowserViewModel.fileListState


    when (val result = state.value) {

        is Result.Error -> Text(text = result.error ?: "")

        is Result.Success -> FileBrowserList(result.data!!, modifier)
        else -> {}
    }


}

@Composable
fun FileBrowserList(data: List<File>, modifier: Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(data.size) {
            FileItem(data[it])

        }
    }
}

@Composable
fun FileItem(file: File) {
    Row(Modifier.fillMaxWidth()) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = file.name,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(
            text = file.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = Typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = file.name,
            modifier = Modifier.padding(horizontal = 5.dp)
        )

    }
}
