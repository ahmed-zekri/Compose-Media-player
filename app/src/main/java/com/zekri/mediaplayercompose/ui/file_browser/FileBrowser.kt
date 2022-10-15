package com.zekri.mediaplayercompose.ui.file_browser

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zekri.mediaplayercompose.common.Result
import com.zekri.mediaplayercompose.common.getAudioInfo
import com.zekri.mediaplayercompose.data.FileType
import com.zekri.mediaplayercompose.domain.AppContainer
import com.zekri.mediaplayercompose.ui.Routes
import com.zekri.mediaplayercompose.ui.theme.Typography
import java.io.File

@Composable
fun FileBrowser(
    appContainer: AppContainer,
    modifier: Modifier,
    navHostController: NavHostController,
    fileType: FileType
) {
    val fileBrowserViewModel = FileBrowserViewModel(
        appContainer.fileRepository,
        (LocalContext.current as Activity).application,
        fileType = fileType
    )
    val state = fileBrowserViewModel.fileListState

    when (val result = state.value) {
        is Result.Error -> Text(text = result.error ?: "")
        is Result.Success -> {
            val data = remember {
                result.data!!
            }
            FileBrowserList(data, modifier, navHostController, fileType)
        }
        else -> {}
    }
}
@Composable
fun FileBrowserList(
    data: List<File>,
    modifier: Modifier,
    navHostController: NavHostController,
    fileType: FileType = FileType.AUDIO
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(data.size,
            {
                data[it]
            }) {
            FileItem(data[it], navHostController, data, fileType)
        }
    }
}
@Composable
fun FileItem(
    file: File,
    navHostController: NavHostController,
    fileList: List<File>,
    fileType: FileType = FileType.AUDIO
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        if (fileType == FileType.AUDIO) {
            navHostController.currentBackStackEntry?.savedStateHandle?.set("audio", file)
            navHostController.currentBackStackEntry?.savedStateHandle?.set("audioFiles", fileList)
            navHostController.navigate(Routes.MEDIA_PLAYER)
        }
    }) {
        if (fileType == FileType.IMAGE) Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(data = file).build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 25.dp, start = 10.dp, end = 7.dp)
            ) {
                Icon(
                    imageVector = if (fileType == FileType.AUDIO) Icons.Default.PlayCircle else Icons.Default.Image,
                    contentDescription = file.name,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Text(
                    text = file.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1
                )

                Spacer(modifier = Modifier.weight(1f))

                if (fileType == FileType.AUDIO) Text(
                    text = file.getAudioInfo().duration ?: "",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.titleMedium,
                )
            }
            if (fileType == FileType.AUDIO) {
                Row {
                    file.getAudioInfo().date?.apply {
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = this,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.subtitle2,
                        )
                    }
                }
            }
        }

        Divider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
    }
}