package com.henra.perqaragame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henra.perqaragame.data.model.uimodel.ExtraDetailGame
import com.henra.perqaragame.data.model.uimodel.Game
import com.henra.perqaragame.data.onFailed
import com.henra.perqaragame.data.onSuccess
import com.henra.perqaragame.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailGameViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow().buffer(3)

    fun fetchDetailGame(id: Int) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading(true))
            repository.fetchDetailGame(id)
                .collect { state ->
                    state.onSuccess { detail ->
                        _uiState.emit(UiState.Loading(false))
                        _uiState.emit(UiState.DetailGame(detail))
                    }.onFailed { error ->
                        _uiState.emit(UiState.Loading(false))
                        _uiState.emit(UiState.Error(error.message ?: "Something Wrong"))
                    }
                }
        }
    }

    fun setFavoriteGame(game: Game, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateGame(game.copy(isFavorite = isFavorite))
        }
    }
}

sealed class UiState {
    object Idle : UiState()
    data class DetailGame(val data: ExtraDetailGame) : UiState()
    data class Error(val error: String) : UiState()
    data class Loading(val isShowLoading: Boolean) : UiState()
}