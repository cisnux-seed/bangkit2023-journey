package dev.cisnux.mywebview

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.cisnux.mywebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val webViewSettings = binding.webView.settings

        webViewSettings.javaScriptEnabled = true
        webViewSettings.allowFileAccess = false
        webViewSettings.allowContentAccess = false
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R)
            webViewSettings.allowUniversalAccessFromFileURLs = false
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                view.loadUrl("javascript:alert('Web Dicoding berhasil dimuat')")
            }
        }
        binding.webView
            .webChromeClient = object : WebChromeClient() {

            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                Toast.makeText(
                    this@MainActivity,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
                result.confirm()
                return true
            }
        }
        binding.webView.loadUrl("https://www.dicoding.com")
    }
}