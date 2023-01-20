package com.henra.perqaragame.core

import android.content.Context
import androidx.room.Room
import com.henra.perqaragame.data.source.local.GameDao
import com.henra.perqaragame.data.source.local.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideIoCoroutineDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideGameDatabase(@ApplicationContext context: Context): GameDB =
        Room
            .databaseBuilder(context, GameDB::class.java, "games_database")
            .build()

    @Singleton
    @Provides
    fun provideGameDao(gameDB: GameDB): GameDao = gameDB.getGameDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(gameDB: GameDB): RemoteKeyDao = gameDB.getRemoteDao()
}