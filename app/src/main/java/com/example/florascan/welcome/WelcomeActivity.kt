package com.example.florascan.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.florascan.MainActivity
import com.example.florascan.SettingViewModel
import com.example.florascan.databinding.ActivityWelcomeBinding
import com.example.florascan.factory.AuthViewModelFactory
import com.example.florascan.ui.login.LoginActivity
import com.example.florascan.ui.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    private val welcomeViewModel: WelcomeViewModel by viewModels {
        AuthViewModelFactory(this)
    }

    private val viewModel: SettingViewModel by viewModels {
        AuthViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
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

        AnimatorSet().apply {
            playSequentially(
                createFadeInAnimator(binding.titleTextView),
                createFadeInAnimator(binding.descTextView),
                AnimatorSet().apply { playTogether(
                    createFadeInAnimator(binding.loginButton),
                    createFadeInAnimator(binding.signupButton)
                )}
            )
            start()
        }
    }

    private fun createFadeInAnimator(view: View): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).setDuration(500)
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        welcomeViewModel.getSession().observe(this) { user ->
            if (user.isNotEmpty()) {
                navigateToMainActivity()
            }
        }

        binding.loginButton.setOnClickListener {
            navigateToLoginActivity()
        }

        binding.signupButton.setOnClickListener {
            navigateToSignupActivity()
        }

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun navigateToSignupActivity() {
        startActivity(Intent(this, SignupActivity::class.java))
    }
}