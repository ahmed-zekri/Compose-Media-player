package com.zekri.mediaplayercompose.data

import android.os.Environment
import com.zekri.mediaplayercompose.common.Result
import com.zekri.mediaplayercompose.domain.FileRepository
import java.io.File


class FileRepositoryImpl : FileRepository {
    override fun getAllFilesInDirectory(folderName: String): Result<List<File>> {

        val path = Environment.getExternalStoragePublicDirectory(folderName).path

        val directory = File(path)
        return try {


            Result.Success(directory.listFiles()?.toList() ?: listOf())
        } catch (e: Exception) {
            Result.Error(e.message)

        }
    }


}
