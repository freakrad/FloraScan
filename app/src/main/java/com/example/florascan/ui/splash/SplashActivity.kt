package com.example.florascan.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.florascan.R
import com.example.florascan.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Tambahkan logika untuk menunggu beberapa saat sebelum pindah ke WelcomeActivity
        // Misalnya menggunakan Handler atau Coroutine
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000ms = 3 detik
    }
}
