package com.zekri.mediaplayercompose.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zekri.mediaplayercompose.domain.FileRepository
import java.io.File

class FilePagingSource constructor(private val fileRepository: FileRepository) :
    PagingSource<Int, File>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, File> {
        return try {
            val position = params.key ?: 1
            val response = fileRepository.filesPager(position)
            LoadResult.Page(
                data = response,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, File>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}