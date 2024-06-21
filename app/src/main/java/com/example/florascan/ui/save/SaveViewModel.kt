package com.example.florascan.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.florascan.helper.HistoryResponse
import com.example.florascan.repository.UserRepository
import com.example.florascan.result.Result

class SaveViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getHistory(): LiveData<Result<HistoryResponse>> = userRepository.getHistory()

    fun deleteHistory(id: Int) = userRepository.deleteHistory(id)
}