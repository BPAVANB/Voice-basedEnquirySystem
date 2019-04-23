package com.rccreation.ravindra.sairamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Liveweb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveweb);
        WebView mywebview = (WebView) findViewById(R.id.webView2);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setScrollBarStyle(mywebview.SCROLLBARS_INSIDE_OVERLAY);
        mywebview.loadUrl("https://www.railyatri.in/live-train-status");
    }
}
