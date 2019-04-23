package com.rccreation.ravindra.sairamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Betst extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betst);
        WebView mywebview = (WebView) findViewById(R.id.webView);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setScrollBarStyle(mywebview.SCROLLBARS_INSIDE_OVERLAY);
        mywebview.loadUrl("https://www.railyatri.in/trains-between-stations");
    }
}
