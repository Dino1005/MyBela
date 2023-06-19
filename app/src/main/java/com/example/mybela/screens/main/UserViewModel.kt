package com.example.mybela.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybela.data.User
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {
    private val _state: MutableState<List<String>> = mutableStateOf(emptyList())
    val state: State<List<String>> get() = _state

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val result = getDataFromFireStore()
            _state.value = result
        }
    }

    fun removeSelectedUsers(selectedUsers: List<String>) : List<String> {
        val users = _state.value.toMutableList()
        return users.filter { !selectedUsers.contains(it) }
    }
}

suspend fun getDataFromFireStore() : List<String>{
    val db = Firebase.firestore
    var users = listOf<String>()

    try {
        val result = db.collection("users").get().await()
        for (document in result){
            val user = document.toObject(User::class.java)
            users += user.name
        }
    }catch(e: FirebaseFirestoreException){
        Log.d("error", "getDataFromFireStore: $e")
    }

    return users
}