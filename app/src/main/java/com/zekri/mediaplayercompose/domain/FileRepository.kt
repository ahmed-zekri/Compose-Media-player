package com.zekri.mediaplayercompose.domain

import java.io.File

interface FileRepository {
    fun getFiles(page: Int): List<File>
}