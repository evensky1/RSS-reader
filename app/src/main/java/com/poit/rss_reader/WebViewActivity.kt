package com.poit.rss_reader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var currentLocation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(intent?.getStringExtra("url") ?: "https://www.google.com")
        currentLocation = intent.getStringExtra("currLoc") ?: "";
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("currLoc", currentLocation)
                startActivity(intent)
                this@WebViewActivity.finish()
            }
        })

    }
}