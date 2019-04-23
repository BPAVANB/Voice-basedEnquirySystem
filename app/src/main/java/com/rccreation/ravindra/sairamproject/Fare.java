package com.rccreation.ravindra.sairamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Fare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare);
        WebView mywebview = (WebView) findViewById(R.id.webView3);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setScrollBarStyle(mywebview.SCROLLBARS_INSIDE_OVERLAY);
        mywebview.loadUrl("https://erail.in/train-fare");
    }
}
