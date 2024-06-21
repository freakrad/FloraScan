package com.example.florascan.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.florascan.MainActivity
import com.example.florascan.R
import com.example.florascan.databinding.ActivityLoginBinding
import com.example.florascan.factory.AuthViewModelFactory
import com.example.florascan.response.LoginResult

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels {
        AuthViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 2f).setDuration(300)
        val emailTv = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 2f).setDuration(300)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 2f).setDuration(300)
        val passwordTv = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 2f).setDuration(300)
        val emailEt = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 2f).setDuration(300)
        val passwordEt = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 2f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(title, emailTv, emailEt, passwordTv, passwordEt, login)
            start()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            handleLogin(email, password)
        }
    }

    private fun handleLogin(email: String, password: String) {
        loginViewModel.login(email, password).observe(this@LoginActivity) { result ->
            result?.let {
                when (it) {
                    is com.example.florascan.result.Result.Loading -> showLoading(true)
                    is com.example.florascan.result.Result.Success -> handleLoginSuccess(it.data.loginResult)
                    is com.example.florascan.result.Result.Error -> handleError(it.error)
                }
            }
        }
    }

    private fun handleLoginSuccess(loginResult: LoginResult?) {
        showLoading(false)
        loginResult?.token?.let { token ->
            loginViewModel.saveTokenSession(token)
        }
        showAlert(getString(R.string.success), getString(R.string.success_login), true)
    }

    private fun handleError(error: String) {
        showLoading(false)
        showAlert(getString(R.string.failed_login), error, false)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showAlert(title: String, message: String, isSuccess: Boolean) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                if (isSuccess) navigateToMain()
            }
            create()
            show()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }
}