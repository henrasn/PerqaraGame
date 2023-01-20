package com.henra.perqaragame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.henra.perqaragame.data.model.uimodel.Game
import com.henra.perqaragame.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<Game>?>(null)
    val uiState = _uiState.asSharedFlow()

    fun fetchGames(query: String = "") {
        viewModelScope.launch {
            repository.getGamesPaging(query)
                .cachedIn(viewModelScope)
                .collect { _uiState.emit(it) }
        }
    }

    fun fetchFavoriteGames() {
        viewModelScope.launch {
            repository.getFavoriteGames()
                .cachedIn(viewModelScope)
                .collect { _uiState.emit(it) }
        }
    }
}