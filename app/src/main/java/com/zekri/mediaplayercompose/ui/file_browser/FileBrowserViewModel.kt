package com.zekri.mediaplayercompose.ui.file_browser

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.zekri.mediaplayercompose.common.Result
import com.zekri.mediaplayercompose.data.FileType
import com.zekri.mediaplayercompose.domain.FileRepository
import java.io.File

class FileBrowserViewModel(
    fileRepository: FileRepository,
    application: Application,
    val fileType: FileType = FileType.AUDIO
) :
    AndroidViewModel(application) {
    private val _fileListState: MutableState<Result<List<File>>> = mutableStateOf(Result.Loading())
    val fileListState: State<Result<List<File>>> = _fileListState

    init {
        _fileListState.value =
            fileRepository.getAllFiles(getApplication(), fileType)
    }
}