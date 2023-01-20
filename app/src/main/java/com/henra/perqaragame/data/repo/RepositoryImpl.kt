package com.henra.perqaragame.data.repo

import androidx.paging.Pager
import androidx.paging.PagingData
import com.henra.perqaragame.core.GameDB
import com.henra.perqaragame.data.DataState
import com.henra.perqaragame.data.mapSuccess
import com.henra.perqaragame.data.model.dto.GamesItem
import com.henra.perqaragame.data.model.uimodel.ExtraDetailGame
import com.henra.perqaragame.data.model.uimodel.Game
import com.henra.perqaragame.data.source.remote.GameRemoteSource
import com.henra.perqaragame.utils.PageUtils
import com.henra.perqaragame.utils.Paging
import com.henra.perqaragame.utils.PagingDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteSource: GameRemoteSource,
    private val localSource: GameDB,
    private val dispatcher: CoroutineDispatcher
) : Repository {
    override fun getGamesPaging(query: String): Flow<PagingData<Game>> {
        return PageUtils.createPager {
            Paging { loadParams ->
                val page = loadParams.key ?: PageUtils.FIRST_PAGE

                when (val response = remoteSource.fetchGames(
                    page = page,
                    size = PageUtils.NETWORK_PAGE_SIZE,
                    query = query
                )) {
                    is DataState.Failed -> PagingDataModel(listOf(), false, 0, response.error)
                    is DataState.Success -> {
                        val count = response.data.count
                        val games = response.data.games.map { game -> game.toUiModel(page) }
                        PagingDataModel(games, true, count)
                    }
                }
            }
        }.flow
    }

    override suspend fun getFavoriteGames(): Flow<PagingData<Game>> {
        return Pager(
            config = PageUtils.createPagingConfig(),
            pagingSourceFactory = { localSource.getGameDao().getFavoriteGames() }
        ).flow
    }

    override suspend fun fetchDetailGame(id: Int): Flow<DataState<ExtraDetailGame>> {
        return remoteSource.fetchDetailGame(id).map { state ->
            state.mapSuccess { response ->
                DataState.Success(
                    ExtraDetailGame(
                        publisher = response.publishers.firstOrNull()?.name.orEmpty(),
                        description = response.description,
                        playTime = response.playtime
                    )
                )
            }
        }.flowOn(dispatcher)
    }

    override suspend fun updateGame(game: Game) {
        localSource.getGameDao().insert(game)
    }
}

fun GamesItem.toUiModel(page: Int): Game {
    return Game(
        id = id,
        name = name,
        releaseDate = released,
        cover = backgroundImage,
        rate = rating,
        page = page
    )
}