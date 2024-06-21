package com.example.florascan.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.florascan.helper.ResponseMl
import com.example.florascan.repository.UserRepository
import com.example.florascan.result.Result
import okhttp3.MultipartBody

class ScanViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun uploadImage(file: MultipartBody.Part): LiveData<Result<ResponseMl>> =
        userRepository.uploadImage(file)

}