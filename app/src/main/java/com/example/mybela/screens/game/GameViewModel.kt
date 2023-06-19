package com.example.mybela.screens.game

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _state: MutableStateFlow<List<Game>> = MutableStateFlow(emptyList())
    val state: StateFlow<List<Game>> = _state.asStateFlow()

    fun updateGame(game: Game) {
        val currentState = _state.value
        val gameIndex = currentState.indexOfFirst { it.id == game.id }
        if (gameIndex != -1) {
            val newState = currentState.toMutableList()
            newState[gameIndex] = game
            _state.value = newState
        }
    }

    fun addGame(game: Game) {
        val currentState = _state.value.toMutableList()
        currentState.add(game)
        _state.value = currentState
    }

    fun resetGame() {
        _state.value = emptyList()
    }
}