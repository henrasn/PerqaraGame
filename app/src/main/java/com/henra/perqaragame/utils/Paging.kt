package com.henra.perqaragame.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState

class Paging<T : Any>(
    private val onResponse: suspend (LoadParams<Int>) -> PagingDataModel<T>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val response = onResponse.invoke(params)
            val prevKey = PageUtils.getPrevPage(params)
            val nextKey = PageUtils.getNextPage(params, response.count)
            if (response.isSuccess) {
                LoadResult.Page(
                    data = response.data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else LoadResult.Error(response.error ?: Exception("Something Wrong"))
        } catch (exception: Throwable) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

data class PagingDataModel<T>(
    val data: List<T>,
    val isSuccess: Boolean,
    val count: Int,
    val error: Exception? = null
)