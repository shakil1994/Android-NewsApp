package com.example.shakil.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

public class DetailsArticle extends AppCompatActivity {

    WebView webView;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(this).build();
        dialog.show();

        /*WebView*/
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

        if (getIntent() != null) {
            if (!getIntent().getStringExtra("webURL").isEmpty()){
                webView.loadUrl(getIntent().getStringExtra("webURL"));
            }
        }
    }
}
