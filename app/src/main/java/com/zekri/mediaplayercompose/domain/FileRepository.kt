package com.zekri.mediaplayercompose.domain

import java.io.File

interface FileRepository {
    fun filesPager(page: Int): List<File>
}