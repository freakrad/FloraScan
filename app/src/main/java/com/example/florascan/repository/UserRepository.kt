package com.example.florascan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.florascan.helper.ApiServiceMl
import com.example.florascan.helper.HistoryResponse
import com.example.florascan.helper.ResponseMl
import com.example.florascan.pref.UserPreference
import com.example.florascan.response.DeleteResponse
import com.example.florascan.response.ErrorResponse
import com.example.florascan.response.LoginResponse
import com.example.florascan.response.RegisterResponse
import com.example.florascan.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiServiceMl,
) {

    suspend fun saveSession(token: String) {
        userPreference.saveSession(token)
    }

    fun getSession(): Flow<String> {
        return userPreference.getSession()
    }

    suspend fun saveTheme(isDarkModeActive: Boolean) {
        userPreference.saveThemeSetting(isDarkModeActive)
    }

    fun getTheme(): Flow<Boolean> {
        return userPreference.getThemeSetting()
    }

    suspend fun logout() {
        userPreference.logout()
    }


    fun postRegister(
        email: String,
        name: String,
        password: String,
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(email, name, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postLogin(
        email: String,
        password: String,
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            errorMessage?.let { Result.Error(it) }?.let { emit(it) }
        }
    }

    fun uploadImage(
        file: MultipartBody.Part,
    ): LiveData<Result<ResponseMl>> = liveData {
        emit(Result.Loading)
        try {
            val token = runBlocking { userPreference.getSession().first() }
            val bearerToken = "Bearer $token"
            val response = apiService.uploadImage(file, bearerToken)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun getHistory(): LiveData<Result<HistoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val token = runBlocking { userPreference.getSession().first() }
            val bearerToken = "Bearer $token"
            val response = apiService.getHistory(bearerToken)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            errorMessage?.let { Result.Error(it) }?.let { emit(it) }
        }
    }

    fun deleteHistory(id: Int): LiveData<Result<DeleteResponse>> = liveData {
        emit(Result.Loading)
        try {
            val token = runBlocking { userPreference.getSession().first() }
            val bearerToken = "Bearer $token"
            val response = apiService.deleteHistory(bearerToken, id)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiServiceMl,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}