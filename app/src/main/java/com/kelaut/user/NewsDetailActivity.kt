package com.kelaut.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.widget.Toolbar

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val URL: String = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Selamat Membaca"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val newsUrl = intent.getStringExtra(URL) ?: ""

        val webView:WebView = findViewById(R.id.wv_news_detail)
        webView.loadUrl(newsUrl)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}