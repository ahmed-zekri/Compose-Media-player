package com.zekri.mediaplayercompose.data

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import com.zekri.mediaplayercompose.common.PAGE_LENGTH
import com.zekri.mediaplayercompose.domain.FileRepository
import java.io.File

class FileRepositoryImpl(private val context: Context, private val fileType: FileType) :
    FileRepository {
    private var files = listOf<File>()

    init {
        getAllFiles()
    }

    private fun getAllFiles() {
        val items: MutableMap<String, Long> = mutableMapOf()
        val musicResolver: ContentResolver = context.contentResolver
        val uris = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when (fileType) {
                FileType.AUDIO -> listOf(
                    MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                    MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_INTERNAL)
                )
                FileType.IMAGE -> listOf(
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_INTERNAL)
                )
                FileType.VIDEO -> listOf(
                    MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                    MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_INTERNAL)
                )
            }
        } else {
            return
        }

        uris.forEach {
            val cursor: Cursor? = musicResolver.query(it, null, null, null, null)

            if (cursor != null && cursor.moveToFirst()) {
                //get columns
                val data: Int = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                val duration: Int =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                do {
                    cursor.getString(data)?.apply {
                        items[this] = cursor.getLong(duration)
                    }
                } while (cursor.moveToNext())
            }
            cursor?.close()
        }
        files = items.toList().sortedBy { (_, value) -> value }.reversed()
            .toMap().keys.filter {
                File(it).run { exists() && !isDirectory }
            }.map { File(it) }
    }

    override fun getFiles(page: Int): List<File> =
        files.subList(PAGE_LENGTH * page, PAGE_LENGTH * page + PAGE_LENGTH)
}

enum class FileType {
    IMAGE, AUDIO, VIDEO
}