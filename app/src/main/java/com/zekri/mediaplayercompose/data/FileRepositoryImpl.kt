package com.zekri.mediaplayercompose.data

import android.os.Build
import android.os.Environment
import com.zekri.mediaplayercompose.common.AUDIO_TYPES
import com.zekri.mediaplayercompose.common.Result
import com.zekri.mediaplayercompose.domain.FileRepository
import java.io.File


class FileRepositoryImpl : FileRepository {
    override fun getAllFilesInDirectory(folderName: String): Result<List<File>> {

        val pathInternal: File? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            File(Environment.getStorageDirectory().path, folderName)
        } else {
            null
        }

        val pathExternal = Environment.getExternalStoragePublicDirectory(folderName)

        return try {

            val condition: (File) -> Boolean = {
                it.extension.lowercase() in AUDIO_TYPES
            }
            Result.Success(
                (pathExternal.listFiles()?.toList()?.filter(condition)
                    ?: listOf()) + (pathInternal?.listFiles()?.filter(condition)?.toList()
                    ?: listOf())
            )
        } catch (e: Exception) {
            Result.Error(e.message)

        }
    }


}
