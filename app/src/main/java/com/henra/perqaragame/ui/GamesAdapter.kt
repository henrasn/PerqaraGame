package com.henra.perqaragame.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.henra.perqaragame.data.model.uimodel.Game
import com.henra.perqaragame.data.model.uimodel.GameDiffUtil
import com.henra.perqaragame.databinding.ItemGameBinding
import com.henra.perqaragame.utils.loadImage
import com.henra.perqaragame.utils.viewBinding

class GamesAdapter(
    private val onClick: (Game) -> Unit
) : PagingDataAdapter<Game, GamesAdapter.GamesViewHolder>(GameDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        return GamesViewHolder(parent.viewBinding(ItemGameBinding::inflate))
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        getItem(position)?.let { game -> holder.bind(game) }
    }

    inner class GamesViewHolder(private val binding: ItemGameBinding) : ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let(onClick)
            }
        }

        fun bind(game: Game) = with(binding) {
            tvGameName.text = game.name
            tvReleaseDate.text = game.releaseDate
            tvRate.text = String.format("%.1f", game.rate)
            ivCover.loadImage(game.cover)
        }
    }
}