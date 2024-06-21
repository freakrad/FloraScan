package com.example.florascan.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.florascan.repository.UserRepository

class WelcomeViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getSession(): LiveData<String> {
        return userRepository.getSession().asLiveData()
    }
}