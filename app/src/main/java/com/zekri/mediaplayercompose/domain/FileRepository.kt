package com.zekri.mediaplayercompose.domain

import android.content.Context
import com.zekri.mediaplayercompose.common.Result
import com.zekri.mediaplayercompose.data.FileType
import java.io.File

interface FileRepository {
    fun getAllFiles(context: Context, fileType: FileType = FileType.AUDIO): Result<List<File>>
}