package com.example.florascan.di

import android.content.Context
import com.example.florascan.helper.ApiConfigMl
import com.example.florascan.pref.UserPreference
import com.example.florascan.pref.dataStore
import com.example.florascan.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object InjectionUser {
    fun provideRepositoryUser(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiServiceUser = ApiConfigMl.getApiService()
        return UserRepository.getInstance(pref, apiServiceUser)
    }
}