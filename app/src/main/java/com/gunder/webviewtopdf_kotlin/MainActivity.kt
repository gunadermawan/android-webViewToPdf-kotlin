package com.gunder.webviewtopdf_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button

class MainActivity : AppCompatActivity() {
//    membuat objek dari webView
    var printWeb: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        inisialisasi
        val webView = findViewById<View>(R.id.wvMain)
        val savePdf = findViewById<Button>(R.id.btnPdf)
    }
}