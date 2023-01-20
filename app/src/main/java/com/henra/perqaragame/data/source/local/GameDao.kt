package com.henra.perqaragame.data.source.local

import androidx.paging.PagingSource
import androidx.room.*
import com.henra.perqaragame.data.model.uimodel.Game

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<Game>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Query("select * from game where isFavorite==1")
    fun getFavoriteGames(): PagingSource<Int, Game>

    @Query("Select * From game Order By page")
    fun getGames(): PagingSource<Int, Game>

    @Query("Select * From game WHERE id==:id")
    fun getGame(id: Int): Game

    @Query("Delete From game")
    suspend fun clearAllGames()
}