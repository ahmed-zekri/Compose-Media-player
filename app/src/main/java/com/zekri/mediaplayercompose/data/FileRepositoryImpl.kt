package com.zekri.mediaplayercompose.data

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import com.zekri.mediaplayercompose.common.Result
import com.zekri.mediaplayercompose.domain.FileRepository
import java.io.File


class FileRepositoryImpl : FileRepository {

    override fun getAllFilesInDirectory(context: Context, folderName: String): Result<List<File>> {
        val files = mutableListOf<File>()
        val musicResolver: ContentResolver = context.contentResolver
        val musicUris = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listOf(
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL),
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_INTERNAL)
            )
        } else {
            listOf()
        }

        musicUris.forEach {
            val musicCursor: Cursor? = musicResolver.query(it, null, null, null, null)

            if (musicCursor != null && musicCursor.moveToFirst()) {
                //get columns

                val data: Int = musicCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                do {

                    musicCursor.getString(data)?.apply {
                        files.add(File(this))

                    }
                } while (musicCursor.moveToNext())
            }
            musicCursor?.close()
        }
        return Result.Success(files)
    }
}
