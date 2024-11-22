package com.example.c2capp


import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppState : ViewModel() {
    var isLoggedIn = mutableStateOf(false)

    fun logIn() {
        isLoggedIn.value = true
    }

    fun logOut() {
        isLoggedIn.value = false
    }
}