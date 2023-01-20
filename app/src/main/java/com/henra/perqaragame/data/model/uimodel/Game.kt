package com.henra.perqaragame.data.model.uimodel

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "game")
@Parcelize
data class Game(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val releaseDate: String,
    val cover: String,
    val rate: Float,
    val isFavorite: Boolean = false,
    val page: Int
) : Parcelable

object GameDiffUtil : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

}
