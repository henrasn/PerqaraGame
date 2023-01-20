package com.henra.perqaragame.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource

object PageUtils {
    const val FIRST_PAGE = 1
    const val NETWORK_PAGE_SIZE = 10
    private const val PREFETCH_DISTANCE = 1

    fun getPrevPage(params: PagingSource.LoadParams<Int>): Int? {
        val currentPage = params.key ?: (FIRST_PAGE * NETWORK_PAGE_SIZE)
        return if (currentPage == (FIRST_PAGE * NETWORK_PAGE_SIZE)) null
        else currentPage.minus(1)
    }

    fun getNextPage(params: PagingSource.LoadParams<Int>, itemCount: Int): Int? {
        val currentPage = params.key ?: (FIRST_PAGE * NETWORK_PAGE_SIZE)
        return if (currentPage >= itemCount) {
            null
        } else {
            currentPage.plus(NETWORK_PAGE_SIZE)
        }
    }

    fun createPagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            initialLoadSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = PREFETCH_DISTANCE
        )
    }

    fun <T : Any> createPager(pagingSourceFactory: () -> PagingSource<Int, T>) = Pager(
        config = createPagingConfig(),
        pagingSourceFactory = pagingSourceFactory,
    )
}