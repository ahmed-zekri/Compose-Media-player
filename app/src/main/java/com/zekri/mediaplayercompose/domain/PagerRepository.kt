package com.zekri.mediaplayercompose.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PagerRepository {
    fun getFiles(): Flow<PagingData<File>>
}