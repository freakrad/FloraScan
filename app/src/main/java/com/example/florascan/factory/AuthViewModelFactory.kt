package com.example.florascan.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.florascan.MainViewModel
import com.example.florascan.SettingViewModel
import com.example.florascan.di.InjectionUser
import com.example.florascan.ui.login.LoginViewModel
import com.example.florascan.ui.save.SaveViewModel
import com.example.florascan.ui.scan.ScanViewModel
import com.example.florascan.ui.signup.SignupViewModel
import com.example.florascan.welcome.WelcomeViewModel

class AuthViewModelFactory(
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(InjectionUser.provideRepositoryUser(context)) as T
            }

            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(InjectionUser.provideRepositoryUser(context)) as T
            }

            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(InjectionUser.provideRepositoryUser(context)) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(InjectionUser.provideRepositoryUser(context)) as T
            }

            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(InjectionUser.provideRepositoryUser(context)) as T
            }

            modelClass.isAssignableFrom(SaveViewModel::class.java) -> {
                SaveViewModel(InjectionUser.provideRepositoryUser(context)) as T
            }

            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(InjectionUser.provideRepositoryUser(context)) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

}