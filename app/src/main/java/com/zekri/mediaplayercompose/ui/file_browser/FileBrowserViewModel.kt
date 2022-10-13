package com.zekri.mediaplayercompose.ui.file_browser

import android.app.Application
import android.os.Environment
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.zekri.mediaplayercompose.domain.FileRepository
import com.zekri.mediaplayercompose.common.Result
import java.io.File

class FileBrowserViewModel(fileRepository: FileRepository, application: Application) :
    AndroidViewModel(application) {
    private val _fileListState: MutableState<Result<List<File>>> = mutableStateOf(Result.Loading())

    val fileListState: State<Result<List<File>>> = _fileListState

    init {

        _fileListState.value =
            fileRepository.getAllFilesInDirectory(getApplication(), Environment.DIRECTORY_DOWNLOADS)


    }

}