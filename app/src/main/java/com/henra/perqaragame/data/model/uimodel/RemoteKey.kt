package com.henra.perqaragame.data.model.uimodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pokemon_id")
    val gameId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?
)
