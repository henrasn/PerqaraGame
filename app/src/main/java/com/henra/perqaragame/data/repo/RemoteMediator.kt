package com.henra.perqaragame.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.henra.perqaragame.core.GameDB
import com.henra.perqaragame.data.DataState
import com.henra.perqaragame.data.model.dto.GamesResponse
import com.henra.perqaragame.data.model.uimodel.Game
import com.henra.perqaragame.data.model.uimodel.RemoteKey
import com.henra.perqaragame.utils.PageUtils

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator(
    private val gameDB: GameDB,
    private val remoteRequest: suspend (Int) -> DataState<GamesResponse>
) : RemoteMediator<Int, Game>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Game>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PageUtils.FIRST_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        if (loadType == LoadType.REFRESH) {
            gameDB.withTransaction {
                gameDB.getRemoteDao().clearRemoteKeys()
                gameDB.getGameDao().clearAllGames()
            }
        }

        return when (val response = remoteRequest(page)) {
            is DataState.Failed -> MediatorResult.Error(response.error)
            is DataState.Success -> {
                MediatorResult.Success(response.data.games.isEmpty())
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Game>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                gameDB.getRemoteDao().getRemoteKeyByGameID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Game>): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { game ->
            gameDB.getRemoteDao().getRemoteKeyByGameID(game.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Game>): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { game ->
            gameDB.getRemoteDao().getRemoteKeyByGameID(game.id)
        }
    }
}