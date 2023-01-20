package com.henra.perqaragame.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.henra.perqaragame.data.model.uimodel.ExtraDetailGame
import com.henra.perqaragame.data.model.uimodel.Game
import com.henra.perqaragame.databinding.ActivityDetailGameBinding
import com.henra.perqaragame.utils.loadImage
import com.henra.perqaragame.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailGameActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityDetailGameBinding::inflate)
    private val viewModel by viewModels<DetailGameViewModel>()

    companion object {
        fun getDetailGameIntent(contenxt: Context, game: Game): Intent {
            return Intent(contenxt, DetailGameActivity::class.java)
                .putExtra("game", game)
        }

        fun getDetailGameExtra(activity: AppCompatActivity): Game? {
            return activity.intent.getParcelableExtra<Game>("game")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        observerUiState()
        getDetailGameExtra(this)?.let { game ->
            bindGame(game)
            viewModel.fetchDetailGame(game.id)
        }
    }

    private fun observerUiState() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.DetailGame -> bindDetailGame(state.data)
                    is UiState.Error -> showToast(state.error)
                    is UiState.Loading -> if (state.isShowLoading) showToast("Loading")
                    UiState.Idle -> {}
                }
            }
        }
    }

    private fun bindDetailGame(extra: ExtraDetailGame) = with(binding) {
        tvPublisher.text = extra.publisher
        tvPlayTime.text = extra.playTime.toString()
        tvGameDescription.text = HtmlCompat.fromHtml(
            extra.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    private fun bindGame(game: Game) = with(binding) {
        tvGameName.text = game.name
        tvReleaseDate.text = game.releaseDate
        tvRate.text = game.rate.toString()
        ivCover.loadImage(game.cover)
        toolbar.title = game.name

        cbFavorite.apply {
            isChecked = game.isFavorite
            setOnClickListener {
                viewModel.setFavoriteGame(game, binding.cbFavorite.isChecked)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}