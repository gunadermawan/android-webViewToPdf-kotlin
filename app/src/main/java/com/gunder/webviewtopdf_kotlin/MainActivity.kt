package com.gunder.webviewtopdf_kotlin

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
//    membuat objek dari webView
    var printWeb: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        inisialisasi
        val webView = findViewById<WebView>(R.id.wvMain)
        val savePdf = findViewById<Button>(R.id.btnPdf)

//        setting web client
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
//          inisialisasi objek
                printWeb = webView
            }
        }

//        load url
        webView.loadUrl("https://google.com")
//        aksi ketika tombol ditekan
        savePdf.setOnClickListener {
            if (printWeb != null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    printTheWebPage(printWeb!!)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "tidak tersedia untuk lolipop",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "laman belum dimuat secara penuh",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
     }

//    membuat objek printjob
    var printJob: PrintJob? = null

//    boolean untuk kontrol status printWeb
    var printBtnPressed = false

//    set minimal API
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun printTheWebPage(webView: WebView) {
        printBtnPressed = true

//    membuat instance printManager
    val prinManager = this
        .getSystemService(PRINT_SERVICE) as PrintManager
//    setting nama job
    val jobname = getString(R.string.app_name) + "Web Page" + webView.url

    val printDocumentAdapter = webView.createPrintDocumentAdapter(jobname)
    printJob = prinManager.print(
        jobname, printDocumentAdapter,
        PrintAttributes.Builder().build()
        )
    }

    override fun onResume() {
        super.onResume()
        if (printJob != null && printBtnPressed){
            when {
                printJob!!.isCompleted -> Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show()
                printJob!!.isStarted -> Toast.makeText(this, "Started!", Toast.LENGTH_SHORT).show()
                printJob!!.isBlocked -> Toast.makeText(this, "Blocked!", Toast.LENGTH_SHORT).show()
                printJob!!.isCancelled -> Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show()
                printJob!!.isFailed -> Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                printJob!!.isQueued -> Toast.makeText(this, "Queued!", Toast.LENGTH_SHORT).show()
            }
            printBtnPressed = false
        }
    }
}

