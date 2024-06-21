package com.example.florascan.ui.signup

import androidx.lifecycle.ViewModel
import com.example.florascan.repository.UserRepository

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(email: String, name: String, password: String) =
        userRepository.postRegister(email, name, password)
}