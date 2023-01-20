package com.henra.perqaragame.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henra.perqaragame.data.model.uimodel.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: RemoteKey)

    @Query("Select * From remote_key Where pokemon_id = :id")
    suspend fun getRemoteKeyByGameID(id: Int): RemoteKey?

    @Query("Delete From remote_key")
    suspend fun clearRemoteKeys()
}