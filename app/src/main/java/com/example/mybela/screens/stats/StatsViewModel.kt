package com.example.mybela.screens.stats

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StatsViewModel : ViewModel() {
    private val _state: MutableState<List<PlayedGame>> = mutableStateOf(emptyList())
    val state: State<List<PlayedGame>> get() = _state

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            val result = getDataFromFireStore()
            _state.value = result
        }
    }
}

suspend fun getDataFromFireStore() : List<PlayedGame>{
    val db = Firebase.firestore
    val games = mutableListOf<PlayedGame>()

    try {
        val result = db.collection("games").get().await()
        for (document in result){
            val game = document.toObject(PlayedGame::class.java)
            games.add(game)
        }
    }catch(e: FirebaseFirestoreException){
        Log.d("error", "getDataFromFireStore: $e")
    }

    return games.reversed()
}