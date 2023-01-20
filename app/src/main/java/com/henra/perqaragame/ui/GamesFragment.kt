package com.henra.perqaragame.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.henra.perqaragame.R
import com.henra.perqaragame.databinding.FragmentGamesBinding
import com.henra.perqaragame.utils.SpaceDecoration
import com.henra.perqaragame.utils.dp
import com.henra.perqaragame.utils.isVisible
import com.henra.perqaragame.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class GamesFragment : Fragment(R.layout.fragment_games) {
    companion object {
        fun newInstance(isHomeView: Boolean): GamesFragment {
            return GamesFragment().apply {
                arguments = Bundle().apply { putBoolean("isHomeView", isHomeView) }
            }
        }
    }

    private val isHomeView by lazy { arguments?.getBoolean("isHomeView", false) ?: false }
    private val binding by viewBinding(FragmentGamesBinding::bind)
    private val viewModel by viewModels<GamesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            val gameAdapter = GamesAdapter { game ->
                context?.let { startActivity(DetailGameActivity.getDetailGameIntent(it, game)) }
            }

            edtSearch.isVisible = isHomeView
            rvGames.apply {
                layoutManager = LinearLayoutManager(this.context)
                addItemDecoration(
                    SpaceDecoration(
                        space = 16.dp,
                        SpaceDecoration.VERTICAL,
                        start = 16.dp,
                        side = 16.dp
                    )
                )
                adapter = gameAdapter
            }

            lifecycleScope.launchWhenStarted {
                viewModel.uiState.collectLatest { state ->
                    state?.let { gameAdapter.submitData(it) }
                }
            }

            if (isHomeView) viewModel.fetchGames()
            else viewModel.fetchFavoriteGames()

            edtSearch.apply {
                doOnTextChanged { text, _, _, _ ->
                    if (text?.isEmpty() == true) viewModel.fetchGames()
                }

                setOnEditorActionListener { _, _, _ ->
                    viewModel.fetchGames(edtSearch.text.toString())
                    true
                }
            }
        }
    }
}