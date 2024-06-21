package com.example.florascan.ui.news

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.florascan.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        // Enable the back button in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        val url = intent.getStringExtra(EXTRA_URL)
        if (url != null) {
            webView.loadUrl(url)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_URL = "extra_url"
    }
}
