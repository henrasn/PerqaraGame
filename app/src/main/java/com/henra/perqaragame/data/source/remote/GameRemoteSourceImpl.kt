package com.henra.perqaragame.data.source.remote

import com.henra.perqaragame.data.DataState
import com.henra.perqaragame.data.model.dto.DetailGameResponse
import com.henra.perqaragame.data.model.dto.GamesResponse
import com.henra.perqaragame.utils.fetchApi
import com.henra.perqaragame.utils.fetchApiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRemoteSourceImpl @Inject constructor(
    private val apiService: ApiService
) : GameRemoteSource {
    override suspend fun fetchGames(page: Int, size: Int, query: String): DataState<GamesResponse> {
        return fetchApi { apiService.fetchGames(page, size, query) }
    }

    override suspend fun fetchDetailGame(id: Int): Flow<DataState<DetailGameResponse>> {
        return fetchApiFlow { apiService.fetchDetailGame(id) }
    }
}