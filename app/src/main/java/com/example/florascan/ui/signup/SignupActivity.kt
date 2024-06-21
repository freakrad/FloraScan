package com.example.florascan.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.florascan.R
import com.example.florascan.databinding.ActivitySignupBinding
import com.example.florascan.factory.AuthViewModelFactory
import com.example.florascan.result.Result

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val signUpViewModel: SignupViewModel by viewModels {
        AuthViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
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
                createFadeInAnimator(binding.nameTextView),
                createFadeInAnimator(binding.nameEditTextLayout),
                createFadeInAnimator(binding.emailTextView),
                createFadeInAnimator(binding.emailEditTextLayout),
                createFadeInAnimator(binding.passwordTextView),
                createFadeInAnimator(binding.passwordEditTextLayout),
                createFadeInAnimator(binding.signupButton)
            )
            start()
        }
    }

    private fun createFadeInAnimator(view: View): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).setDuration(300)
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
        binding.signupButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            signUpViewModel.register(email, name, password).observe(this@SignupActivity) { result ->
                result?.let {
                    when (it) {
                        is Result.Loading -> binding.progressBarSignup.visibility = View.VISIBLE
                        is Result.Success -> handleSuccess()
                        is Result.Error -> handleError(it.error)
                    }
                }
            }
        }
    }

    private fun handleSuccess() {
        binding.progressBarSignup.visibility = View.GONE
        AlertDialog.Builder(this@SignupActivity).apply {
            setTitle(getString(R.string.success))
            setMessage(getString(R.string.success_register))
            setPositiveButton(getString(R.string.next)) { _, _ -> finish() }
            create()
            show()
        }
    }

    private fun handleError(error: String) {
        binding.progressBarSignup.visibility = View.GONE
        AlertDialog.Builder(this@SignupActivity).apply {
            setTitle(getString(R.string.failed_register))
            setMessage(error)
            setPositiveButton(getString(R.string.ok), null)
            create()
            show()
        }
    }
}