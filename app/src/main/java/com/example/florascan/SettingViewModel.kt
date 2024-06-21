package com.example.florascan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.florascan.repository.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return userRepository.getTheme().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            userRepository.saveTheme(isDarkModeActive)
        }
    }
}