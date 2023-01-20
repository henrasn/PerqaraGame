package com.henra.perqaragame.data.repo

import androidx.paging.PagingData
import com.henra.perqaragame.data.DataState
import com.henra.perqaragame.data.model.uimodel.ExtraDetailGame
import com.henra.perqaragame.data.model.uimodel.Game
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getGamesPaging(query: String = ""): Flow<PagingData<Game>>
    suspend fun fetchDetailGame(id: Int): Flow<DataState<ExtraDetailGame>>
    suspend fun updateGame(game: Game)
    suspend fun getFavoriteGames(): Flow<PagingData<Game>>
}