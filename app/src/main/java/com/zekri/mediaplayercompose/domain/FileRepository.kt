package com.zekri.mediaplayercompose.domain

import android.content.Context
import com.zekri.mediaplayercompose.common.Result
import java.io.File

interface FileRepository {
    fun getAllFilesInDirectory(context: Context,folderName: String): Result<List<File>>
}