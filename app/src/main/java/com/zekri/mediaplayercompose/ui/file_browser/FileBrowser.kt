package com.zekri.mediaplayercompose.ui.file_browser

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zekri.mediaplayercompose.common.getAudioInfo
import com.zekri.mediaplayercompose.data.AppContainerImpAudio
import com.zekri.mediaplayercompose.domain.AppContainer
import com.zekri.mediaplayercompose.ui.Routes
import kotlinx.coroutines.flow.Flow
import java.io.File

@Composable
fun FileBrowser(
    appContainer: AppContainer,
    modifier: Modifier,
    navHostController: NavHostController,
) {
    val isAudioFile = remember {
        appContainer is AppContainerImpAudio
    }
    val items = remember {
        mutableStateOf(
            appContainer.pagerRepository.getFiles()
        )
    }
    FileBrowserList(items, modifier, navHostController, isAudioFile)
}
@Composable
fun FileBrowserList(
    data: MutableState<Flow<PagingData<File>>>,
    modifier: Modifier,
    navHostController: NavHostController,
    isAudioFile: Boolean,
) {
    val pagingItems = data.value.collectAsLazyPagingItems()
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(pagingItems) {
            FileItem(
                file = it!!,
                navHostController = navHostController,
                fileList = pagingItems.itemSnapshotList.items, isAudioFile
            )
        }
    }
}
@Composable
fun FileItem(
    file: File, navHostController: NavHostController, fileList: List<File>, isAudioFile: Boolean
) {
    file.apply {
        val duration = remember {
            getAudioInfo().duration
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
            if (isAudioFile) {
                navHostController.currentBackStackEntry?.savedStateHandle?.set("audio", file)
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "audioFiles", fileList
                )
                navHostController.navigate(Routes.MEDIA_PLAYER)
            }
        }) {
            if (!isAudioFile) Row {
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
                        imageVector = if (isAudioFile) Icons.Default.PlayCircle else Icons.Default.Image,
                        contentDescription = name,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    if (isAudioFile) Text(
                        text = duration ?: "",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }

            Divider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
        }
    }
}