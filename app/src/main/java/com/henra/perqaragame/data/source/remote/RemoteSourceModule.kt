package com.henra.perqaragame.data.source.remote

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteSourceModule {

    @Binds
    abstract fun bindRemoteSource(remoteSourceImpl: GameRemoteSourceImpl): GameRemoteSource
}