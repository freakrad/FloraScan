package com.example.florascan.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.florascan.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, password: String) =
        repository.postLogin(email, password)

    fun saveTokenSession(token: String) {
        viewModelScope.launch {
            repository.saveSession(token)
        }
    }

}