package com.example.wa_tools_webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var url: String = "https://cliphy.io/"
    lateinit var mAdView: AdView
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        Admob()
        web2()

        btn1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                webView.loadUrl("https://cliphy.io/edit")
                btn1.visibility = if (btn1.visibility == View.VISIBLE) {
                    View.INVISIBLE
                } else {
                    View.VISIBLE
                }
            }
        })

    }

    private fun initViews() {
        this.mAdView = findViewById(R.id.adView)
        this.webView = findViewById(R.id.webview)
    }

    private fun web2() {
        if (webView != null) {
            val webString = webView!!.settings
            webString.javaScriptEnabled = true

            webView!!.webViewClient = WebViewClient()
            webview!!.webChromeClient = WebChromeClient()
            webview.loadUrl(url)
            webview!!.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    mProgressBar.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    mProgressBar.visibility = View.GONE
                    super.onPageFinished(view, url)
                }
            }
        }
    }

    private fun Admob() {
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        webView = findViewById(R.id.webview)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            btn1.visibility = if (btn1.visibility == View.INVISIBLE) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

        } else {
            super.onBackPressed()
        }
    }
}
