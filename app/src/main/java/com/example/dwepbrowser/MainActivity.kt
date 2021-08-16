package com.example.dwepbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        WebView.loadUrl("https://www.google.com")

        urlEditText.setOnEditorActionListener {_, actionId, _->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                WebView.loadUrl(urlEditText.text.toString())
                true
            }else{
                false
            }
        }

        registerForContextMenu(WebView)

    }

    override fun onBackPressed() {
        if(WebView.canGoBack()){
            WebView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            R.id.action_google, R.id.action_home->{
                WebView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver->{
                WebView.loadUrl("https://m.naver.com")
                return true
            }
            R.id.action_daum->{
                WebView.loadUrl("https://m.daum.net")
                return true
            }
            R.id.action_call->{
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-4289-7979")
                if(intent.resolveActivity(packageManager)!= null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text->{
                WebView.url?.let { sendSMS("010-4289-7979", it) }
                return true
            }
            R.id.action_email->{
                WebView.url?.let { email("dorian21@naver.com", "좋은 사이트", it) }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_share->{
                WebView.url?.let { share(it) }
            }
            R.id.action_browser->{
                WebView.url?.let { browse(it) }
            }
        }
        return super.onContextItemSelected(item)
    }
}