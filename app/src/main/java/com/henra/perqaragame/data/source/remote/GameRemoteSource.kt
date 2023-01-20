package com.henra.perqaragame.data.source.remote

import com.henra.perqaragame.data.DataState
import com.henra.perqaragame.data.model.dto.DetailGameResponse
import com.henra.perqaragame.data.model.dto.GamesResponse
import kotlinx.coroutines.flow.Flow

interface GameRemoteSource {
    suspend fun fetchGames(page: Int, size: Int, query: String = ""): DataState<GamesResponse>
    suspend fun fetchDetailGame(id: Int): Flow<DataState<DetailGameResponse>>
}