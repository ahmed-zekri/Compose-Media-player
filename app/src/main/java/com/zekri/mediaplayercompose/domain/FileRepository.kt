package com.zekri.mediaplayercompose.domain

import com.zekri.mediaplayercompose.common.Result
import java.io.File

interface FileRepository {
    fun getAllFilesInDirectory(folderName: String): Result<List<File>>
}