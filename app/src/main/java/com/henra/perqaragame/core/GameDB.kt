package com.henra.perqaragame.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henra.perqaragame.data.model.uimodel.Game
import com.henra.perqaragame.data.model.uimodel.RemoteKey
import com.henra.perqaragame.data.source.local.GameDao
import com.henra.perqaragame.data.source.local.RemoteKeyDao

@Database(entities = [Game::class, RemoteKey::class], version = 1)
abstract class GameDB : RoomDatabase() {
    abstract fun getGameDao(): GameDao
    abstract fun getRemoteDao(): RemoteKeyDao
}