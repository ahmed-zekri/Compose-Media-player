package com.zekri.mediaplayercompose.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zekri.mediaplayercompose.common.PAGE_LENGTH
import com.zekri.mediaplayercompose.domain.PagerRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class PagerRepositoryImpl(private val filePagingSource: FilePagingSource) : PagerRepository {
    override fun getFiles(): Flow<PagingData<File>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_LENGTH,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                filePagingSource
            }, initialKey = 1
        ).flow
}

