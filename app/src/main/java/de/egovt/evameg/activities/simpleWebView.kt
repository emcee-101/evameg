package de.egovt.evameg.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.MainActivity
import de.egovt.evameg.R

/**
 * A Webview using the Android System Webview component. Is used for the articles of the RSS feed.
 *
 * @author Niklas Herzog
 *
 */
class simpleWebView : AppCompatActivity() {

    private var webView: WebView? = null
    lateinit var button : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(de.egovt.evameg.R.layout.activity_simple_web_view)

        webView = findViewById<View>(de.egovt.evameg.R.id.webView1) as WebView
        webView!!.settings.javaScriptEnabled = true

        val URL = intent.extras!!.getString("url_")

        if (URL != null) {
            webView!!.loadUrl(URL)
        } else
            webView!!.loadUrl("https://erfurt.de")

        button = findViewById(R.id.web_button)
        button.setOnClickListener {

            val myIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)

        }
    }
}