package com.example.wa_tools_webview

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URISyntaxException
import java.util.*


class MainActivity : AppCompatActivity() {
    var url: String = "https://cliphy.io/"
    lateinit var mAdView: AdView
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        adMob()
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
                override fun shouldOverrideUrlLoading(view:WebView,
                                             request: WebResourceRequest
                ):Boolean {
                    var uri = request.getUrl()
                    if (Objects.equals(uri.getScheme(), "whatsapp"))
                    {
                        try
                        {
                            val intent = Intent.parseUri(request.getUrl().toString(), Intent.URI_INTENT_SCHEME)
                            if (intent.resolveActivity(getPackageManager()) != null)
                                startActivity(intent)
                            return true
                        }
                        catch (use: URISyntaxException) {
                            Log.e("Tag", use.getReason())
                        }
                    }
                    if (uri.toString() == "https://cliphy.io/")
                    {
                        try
                        {
                            buttoVisible()
                            return true
                        }
                        catch (use: URISyntaxException) {
                            Log.e("Tag", use.getReason())
                        }
                    }
                    else
                    {
                        btn1.visibility = if (btn1.visibility == View.VISIBLE) {
                            View.INVISIBLE
                        } else {
                            View.INVISIBLE
                        }
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
        }
    }

    private fun adMob() {
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        webView = findViewById(R.id.webview)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
           //buttoVisible()

        } else {
            super.onBackPressed()
        }
    }

    fun buttoVisible(){
        btn1.visibility = if (btn1.visibility == View.INVISIBLE) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}
