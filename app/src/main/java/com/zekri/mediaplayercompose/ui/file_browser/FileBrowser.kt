package com.zekri.mediaplayercompose.ui.file_browser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zekri.mediaplayercompose.common.Result
import com.zekri.mediaplayercompose.common.getAudioInfo
import com.zekri.mediaplayercompose.ui.Routes
import com.zekri.mediaplayercompose.ui.theme.Typography
import java.io.File

@Composable
fun FileBrowser(
    fileBrowserViewModel: FileBrowserViewModel,
    modifier: Modifier,
    navHostController: NavHostController
) {
    val state = fileBrowserViewModel.fileListState


    when (val result = state.value) {

        is Result.Error -> Text(text = result.error ?: "")

        is Result.Success -> FileBrowserList(result.data!!, modifier, navHostController)
        else -> {}
    }


}

@Composable
fun FileBrowserList(data: List<File>, modifier: Modifier, navHostController: NavHostController) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(data.size) {
            FileItem(data[it], navHostController, data)

        }
    }
}

@Composable
fun FileItem(file: File, navHostController: NavHostController, fileList: List<File>) {
    Column(modifier = Modifier.clickable {
        navHostController.currentBackStackEntry?.savedStateHandle?.set("audio", file)
        navHostController.currentBackStackEntry?.savedStateHandle?.set("audioFiles", fileList)
        navHostController.navigate(Routes.MEDIA_PLAYER)
    }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = file.name,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = file.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.titleLarge,
                    modifier = Modifier.width(250.dp)
                )
                file.getAudioInfo().author?.apply {

                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = this,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = Typography.titleLarge,
                        modifier = Modifier.width(250.dp)
                    )
                }

            }


            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = file.getAudioInfo().Duration ?: "",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Typography.titleMedium,
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = file.name,
                modifier = Modifier.padding(horizontal = 5.dp)
            )

        }

        Divider(thickness = 1.dp)
    }
}
