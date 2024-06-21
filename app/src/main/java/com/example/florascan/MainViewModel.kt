package com.example.florascan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.florascan.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository): ViewModel() {
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}